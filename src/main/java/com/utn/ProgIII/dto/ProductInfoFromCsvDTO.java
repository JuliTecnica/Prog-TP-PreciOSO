package com.utn.ProgIII.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties("id")
public record ProductInfoFromCsvDTO(
        @JsonProperty("nombre")
        String name,

        @JsonProperty("precio")
        Double cost) {
}
