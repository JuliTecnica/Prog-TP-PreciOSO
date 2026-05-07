package com.utn.ProgIII.dto;

import java.sql.Timestamp;
import java.util.List;

public record CreatedOrderDTO(
        Long order_id,
        Double total,
        Double final_total,
        String status,
        Timestamp created_at,
        List<OrderItemDTO> orderItems
) {
}
