package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ViewProductCustomer(
        @Schema(example = "1")
        Long idProduct,
        @Schema(example = "Pasas de uva")
        String name,
        @Schema(example = "1.5")
        Integer stock,
        @Schema(example = "200")
        Double price,
        @Schema(example = "1")
        CategoryDTO category
) {

}
