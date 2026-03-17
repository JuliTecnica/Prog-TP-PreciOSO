package com.utn.ProgIII.dto;

import java.util.List;

public record NonAffectedProductsListDTO(
        String message,
        List<ProductInfoFromCsvDTO> nonAffectedProducts
) {
}
