package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ExtendedProductManagerDTONoDollarPrice(
        @Schema(example = "1")
        Long idProductSupplier,
        @Schema(example = "1")
        Long idProduct,
        @Schema(example = "Pasas de uva")
        String name,
        @Schema(example = "200")
        Double cost,
        @Schema(example = "not available")
        String dollarPrice
) {
}
