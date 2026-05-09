package com.utn.ProgIII.service.implementations;

import com.querydsl.core.BooleanBuilder;
import com.utn.ProgIII.dto.*;
import com.utn.ProgIII.exceptions.BadRequestException;
import com.utn.ProgIII.exceptions.InternalServerError;
import com.utn.ProgIII.exceptions.InvalidRequestException;
import com.utn.ProgIII.exceptions.NotFoundException;
import com.utn.ProgIII.mapper.OrderMapper;
import com.utn.ProgIII.model.Order.OrderDetails;
import com.utn.ProgIII.model.Order.OrderItem;
import com.utn.ProgIII.model.Order.OrderStatus;
import com.utn.ProgIII.model.Order.QOrderDetails;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.repository.OrderRepository;
import com.utn.ProgIII.repository.ProductRepository;
import com.utn.ProgIII.service.interfaces.OrderService;
import com.utn.ProgIII.service.interfaces.UserService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserService userService;

    public OrderServiceImpl(ProductRepository productRepository, OrderRepository orderRepository, OrderMapper orderMapper, UserService userService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userService = userService;
    }

    @Override
    public CreatedOrderDTO createOrder(CreateOrderDTO createOrderDTO) {
        List<OrderItem> item_list = createCartList(createOrderDTO);
        List<String> failures = checkIfStockIsEnough(item_list);

        if(!failures.isEmpty())
        {
            throw new BadRequestException("El stock actual de los siguientes productos es menor que el requerido: " + failures.toString());
        }

        User user = userService.getCurrentUser();

        OrderDetails orderDetails = new OrderDetails();
        item_list.forEach((item) -> item.setOrderDetails(orderDetails));
        orderDetails.setOrderItems(item_list);
        orderDetails.setCreatedAt(Timestamp.from(Instant.now()));
        orderDetails.setTotal(orderDetails.calculateTotal());
        orderDetails.setDiscounted(0.0);
        orderDetails.setUser(user);
        orderDetails.setFinalTotal(orderDetails.calculateTotal());
        orderDetails.setStatus(OrderStatus.WAITING);
        // im very doubtful that we'll actually use discounts and whatnot

        OrderDetails saved = orderRepository.save(orderDetails);
        handleDeletingStock(saved.getOrderItems());

        return orderMapper.toCreatedOrderDTO(saved);
    }

    private List<OrderItem> createCartList(CreateOrderDTO createOrderDTO)
    {
        List<CreateOrderItem> createOrderItemList = createOrderDTO.items().stream().filter((x) -> x.quantity() != 0).toList();
        if(createOrderDTO.items().isEmpty()) throw new BadRequestException("Debe tener al menos 1 elemento en el carrito!");

        List<Long> ids = createOrderDTO.items().stream().map(CreateOrderItem::itemId).toList();
        List<Product> products = productRepository.findAllById(ids).stream().filter((prod) -> prod.getPrice() != null).toList();

        if(products.size() != ids.size()) throw new InternalServerError("Algunos productos no están disponibles!");

        Map<Product, Integer> products_mapping = new HashMap<>();
        // awful, but actually works
        products.forEach((x) -> products_mapping.put(
                x, createOrderItemList.stream().filter(
                        (y) -> Objects.equals(y.itemId(), x.getIdProduct())).findFirst().get().quantity()));

        return orderMapper.toOrderItemEntity(products_mapping);
    }

    private List<String> checkIfStockIsEnough(List<OrderItem> items)
    {
        List<String> failures = new ArrayList<>();
        for (OrderItem item : items)
        {
            Product product = item.getProduct();
            if((product.getStock() - item.getQuantity()) < 0)
            {
                failures.add(product.getName());
            }
        }

        return failures;
    }

    private void handleDeletingStock(List<OrderItem> items)
    {
        for (OrderItem item : items)
        {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
    }


    @Override
    public Page<ViewOrderDTO> getOrdersPage(Pageable pageable, String status, String dni) {

        QOrderDetails QorderDetails = QOrderDetails.orderDetails;
        BooleanBuilder booleanBuilder = new BooleanBuilder().or(QorderDetails.isNotNull());

        if(status != null && !EnumUtils.isValidEnum(OrderStatus.class, status.toUpperCase())) {
            throw new InvalidRequestException("Ese estado no está presente");
        }

        if(status != null)
        {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            booleanBuilder.and(QorderDetails.status.eq(orderStatus));
        }

        if(dni != null)
        {
            booleanBuilder.and(QorderDetails.user.dni.eq(dni));
        }



        return orderRepository.findAll(booleanBuilder,pageable).map(orderMapper::toViewOrderDTO);
    }

    @Override
    public ViewOrderDTO getOrderById(Long id) {
        OrderDetails orderDetails = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Ese pedido no existe!"));


        return orderMapper.toViewOrderDTO(orderDetails);
    }

    @Override
    public void changeOrderStatus(Long id, StateChangeDTO stateChangeDTO) {
        if(stateChangeDTO.status() != null && !EnumUtils.isValidEnum(OrderStatus.class, stateChangeDTO.status().toUpperCase())) {
            throw new InvalidRequestException("Ese estado no está presente");
        }
        OrderDetails orderDetails = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Ese pedido no existe!"));

        OrderStatus orderStatus = OrderStatus.valueOf(stateChangeDTO.status());

        if(orderDetails.getStatus() == OrderStatus.COMPLETE || orderDetails.getStatus() == OrderStatus.CANCELLED)
        {
            throw new InvalidRequestException("No es posible cambiar el estado de la orden si está completa o cancelada!");
        }

        orderDetails.setStatus(orderStatus);

        if(orderDetails.getStatus() == OrderStatus.CANCELLED)
        {
            restoreProductStock(orderDetails.getOrderItems());
        }

        orderRepository.save(orderDetails);
    }

    private void restoreProductStock(List<OrderItem> items)
    {
        for (OrderItem item : items)
        {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }
    }

    @Override
    public Page<ViewOrderDTO> getMyOrders(String status, Pageable pageable) {
        QOrderDetails orderDetails = QOrderDetails.orderDetails;
        BooleanBuilder booleanBuilder = new BooleanBuilder().or(orderDetails.isNotNull());

        if(status != null && !EnumUtils.isValidEnum(OrderStatus.class, status.toUpperCase())) {
            throw new InvalidRequestException("Ese estado no está presente");
        }

        User user = userService.getCurrentUser();

        booleanBuilder.and(orderDetails.user.eq(user));

        if(status != null)
        {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            booleanBuilder.and(orderDetails.status.eq(orderStatus));
        }

        return orderRepository.findAll(booleanBuilder,pageable).map(orderMapper::toViewOrderDTO);
    }
}
