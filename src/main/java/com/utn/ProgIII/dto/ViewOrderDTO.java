package com.utn.ProgIII.dto;

import java.sql.Timestamp;
import java.util.List;

public record ViewOrderDTO(
        Long orderId,
        BasicUserInfoDTO userInfo,
        Double total,
        Double finalTotal,
        String status,
        String createdAt,
        List<OrderItemDTO> orderItems
) {}
