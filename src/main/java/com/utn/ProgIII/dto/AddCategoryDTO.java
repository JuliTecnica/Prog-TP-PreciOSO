package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AddCategoryDTO(
        @Schema(example = "Categoria 1")
        String name
) {
}
