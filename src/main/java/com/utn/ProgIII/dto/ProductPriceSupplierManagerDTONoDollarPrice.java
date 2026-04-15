package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductPriceSupplierManagerDTONoDollarPrice(
        @Schema(example = "1")
        Long idProductSupplier,
        @Schema(example = "1")
        Long idSupplier,
        @Schema(example = "Empresa")
        String companyName,
        @Schema(example = "100")
        Double cost,
        @Schema(example = "not available")
        String dollarPrice
) {

}
