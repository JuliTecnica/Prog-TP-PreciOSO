package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.CreateOrderDTO;
import com.utn.ProgIII.dto.CreatedOrderDTO;
import com.utn.ProgIII.dto.OrderItemDTO;
import com.utn.ProgIII.model.Order.OrderDetails;
import com.utn.ProgIII.model.Order.OrderItem;
import com.utn.ProgIII.model.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrderMapper {

    @Autowired
    private ProductMapper productMapper;

    public List<OrderItem> toOrderItemEntity(Map<Product,Integer> map)
    {
        List<OrderItem> orderItems = new ArrayList<>();

        for(Map.Entry<Product,Integer> value : map.entrySet())
        {
            Product product = value.getKey();
            Integer selected = value.getValue();

            OrderItem orderitem = OrderItem.builder()
                    .product(product)
                    .unit_price(product.getPrice())
                    .quantity(selected)
                    .build();

            orderItems.add(orderitem);
        }

        return orderItems;
    }

    public CreatedOrderDTO toCreatedOrderDTO(OrderDetails orderDetails)
    {
        return new CreatedOrderDTO(
                orderDetails.getOrderDetailsId(),
                orderDetails.getTotal(),
                orderDetails.getFinalTotal(),
                orderDetails.getStatus().toString(),
                orderDetails.getCreatedAt(),
                orderDetails.getOrderItems().stream().map(this::toOrderItemDTO).toList()
                );
    }

    private OrderItemDTO toOrderItemDTO(OrderItem orderItem)
    {
        return new OrderItemDTO(
                productMapper.ProductDTOOrder(orderItem.getProduct()),
                orderItem.getUnit_price(),
                orderItem.getQuantity()
        );
    }
}
