package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Formato que adquieren los datos recibidos desde una request previo a ser enviados al mapper
 */
@Builder
public record CreateUserNoAuthDTO(
        @Schema(example = "Carlitos")
        String firstname,
        @Schema(example = "Testeador")
        String lastname,
        @Schema(example = "12345678")
        String dni,
        CreateCredentialNoAuthDTO credential) {}