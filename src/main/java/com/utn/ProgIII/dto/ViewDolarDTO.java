package com.utn.ProgIII.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ViewDolarDTO(
        @Schema(example = "USD")
        String moneda,
        @Schema(example = "oficial")
        String nombre,
        @Schema(example = "oficial")
        String casa,
        @Schema(example = "1200")
        Double venta,
        @Schema(example = "1150")
        Double compra,
        @Schema(example = "2025-06-11T09:46:00.000Z")
        String ultima_actualizacion
){}
