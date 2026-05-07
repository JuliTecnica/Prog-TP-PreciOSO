package com.utn.ProgIII.model.Order;

import com.utn.ProgIII.model.Product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OrderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product product;

    private Double unit_price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDetails orderDetails;
}
