package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.CreateOrderDTO;
import com.utn.ProgIII.dto.CreateOrderItem;
import com.utn.ProgIII.dto.CreatedOrderDTO;
import com.utn.ProgIII.exceptions.BadRequestException;
import com.utn.ProgIII.exceptions.InternalServerError;
import com.utn.ProgIII.mapper.OrderMapper;
import com.utn.ProgIII.model.Order.OrderDetails;
import com.utn.ProgIII.model.Order.OrderItem;
import com.utn.ProgIII.model.Order.OrderStatus;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.repository.OrderRepository;
import com.utn.ProgIII.repository.ProductRepository;
import com.utn.ProgIII.service.interfaces.OrderService;
import com.utn.ProgIII.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserService userService;

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

}
