package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryDTO(
        @Schema(example = "1")
        Long id,
        @Schema(example = "Categoria 1")
        String name
){
}
