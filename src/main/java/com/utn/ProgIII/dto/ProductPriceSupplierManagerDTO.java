package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductPriceSupplierManagerDTO(
        @Schema(example = "1")
        Long idProductSupplier,
        @Schema(example = "1")
        Long idSupplier,
        @Schema(example = "Empresa")
        String companyName,
        @Schema(example = "100")
        Double cost,
        @Schema(example = "0.01")
        Double dollarPrice
) {

}
