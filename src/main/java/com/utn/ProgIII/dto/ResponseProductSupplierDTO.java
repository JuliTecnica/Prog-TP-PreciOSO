package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ResponseProductSupplierDTO(
        @Schema(example = "1")
        Long idProductSupplier,
        @Schema(example = "1")
        Long idProduct,
        @Schema(example = "Pasas de uva")
        String productName,
        @Schema(example = "1")
        Long idSupplier,
        @Schema(example = "Compañía Test")
        String supplierName,
        @Schema(example = "200")
        BigDecimal cost
//        @Schema(example = "30")
//        BigDecimal profitMargin,
//        @Schema(example = "260")
//        BigDecimal price)
){
}
