package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateOrderItem(
        @Schema(example = "1")
        Long itemId,
        @Schema(example = "20")
        Integer quantity
) {
}
