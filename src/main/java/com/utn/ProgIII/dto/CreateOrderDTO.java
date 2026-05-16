package com.utn.ProgIII.dto;

import java.util.List;

public record CreateOrderDTO(
        List<CreateOrderItem> items
) {
}
