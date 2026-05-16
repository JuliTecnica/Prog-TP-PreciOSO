package com.utn.ProgIII.dto;

import com.utn.ProgIII.model.Product.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProductDTOOrder(
        @Schema(example = "1")
        Long idProduct,
        @Schema(example = "Pasas de uva")
        String name
) {
}
