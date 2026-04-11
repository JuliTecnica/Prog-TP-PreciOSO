package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Product.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder

public record CreateProductDTO(
        @Schema(example = "Pasas de uva")
        String name,
        @Schema(implementation = ProductStatus.class)
        String status,
        @Schema(example = "0.4")
        Double profitMargin,
        @Schema(example = "1.5")
        Integer stock,
        @Schema(example = "1")
        Long idCategory
) {
}
