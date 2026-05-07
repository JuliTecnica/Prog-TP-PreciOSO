package com.utn.ProgIII.dto;

public record OrderItemDTO(
    ProductDTOOrder productInfo,
    Double priceAtOrder,
    Integer quantity
) {
}
