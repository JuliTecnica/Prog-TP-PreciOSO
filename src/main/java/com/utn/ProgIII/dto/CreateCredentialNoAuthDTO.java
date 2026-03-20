package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CreateCredentialNoAuthDTO(
        @Schema(example = "Username_test")
        String username,
        @Schema(example = "password123")
        String password) {}
