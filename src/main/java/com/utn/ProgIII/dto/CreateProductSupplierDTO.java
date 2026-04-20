package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateProductSupplierDTO(
        @Schema(example = "1")
        @NotNull(message = "El ID del producto no puede ser nulo")
        Long idProduct,
        @Schema(example = "1")
        @NotNull(message = "El ID del proveedor no puede ser nulo")
        Long idSupplier,
        @Schema(example = "250")
        @PositiveOrZero(message = "El costo no puede ser menor a cero")
        @NotNull(message = "El costo no puede ser nulo")
        Double cost) {
}
