package com.utn.ProgIII.model.Order;

import com.utn.ProgIII.model.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Double total;
    private Double discounted;
    private Double finalTotal;
    private Timestamp createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderDetails",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Double calculateTotal()
    {
        return orderItems.stream().map((x) -> x.getQuantity() * x.getUnit_price()).reduce(0.0,Double::sum);
    }
}
