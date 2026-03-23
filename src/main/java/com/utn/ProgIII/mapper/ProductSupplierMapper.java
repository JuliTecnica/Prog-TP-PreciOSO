package com.utn.ProgIII.mapper;

import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.dto.ResponseProductSupplierDTO;
import org.springframework.stereotype.Component;

@Component
/*
 * Una clase que se encarga de crear un objeto desde un DTO
 */
public class ProductSupplierMapper {

    public ResponseProductSupplierDTO fromEntityToDto(ProductSupplier productSupplier) {
        return new ResponseProductSupplierDTO(
                productSupplier.getIdProductSupplier(),
                productSupplier.getProduct().getIdProduct(),
                productSupplier.getProduct().getName(),
                productSupplier.getSupplier().getIdSupplier(),
                productSupplier.getSupplier().getCompanyName(),
                productSupplier.getCost()
        );
    }
}

