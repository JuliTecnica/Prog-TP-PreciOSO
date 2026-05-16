package com.utn.ProgIII.dto;

import java.sql.Timestamp;
import java.util.List;

public record CreatedOrderDTO(
        Long orderId,
        Double total,
        Double finalTotal,
        String status,
        Timestamp createdAt,
        List<OrderItemDTO> orderItems
) {
}
