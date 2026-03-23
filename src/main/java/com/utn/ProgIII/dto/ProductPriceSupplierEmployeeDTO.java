package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductPriceSupplierEmployeeDTO(
        @Schema(example = "1")
        Long idSupplier,
        @Schema(example = "Empresa")
        String companyName
//        @Schema(example = "200")
//        BigDecimal price
) {
}
