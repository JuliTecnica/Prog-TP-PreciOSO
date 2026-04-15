package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.*;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
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

    public ExtendedProductManagerDTO fromEntityToExtendedProductDTO(ProductSupplier productSupplier, Double dolar_price)
    {
        return new ExtendedProductManagerDTO(
                productSupplier.getIdProductSupplier(),
                productSupplier.getProduct().getIdProduct(),
                productSupplier.getProduct().getName(),
                productSupplier.getCost(),
                productSupplier.getCost()/dolar_price
        );
    }

    public ExtendedProductManagerDTONoDollarPrice fromEntityToExtendedProductDTODolarless(ProductSupplier productSupplier)
    {
        return new ExtendedProductManagerDTONoDollarPrice(
                productSupplier.getIdProductSupplier(),
                productSupplier.getProduct().getIdProduct(),
                productSupplier.getProduct().getName(),
                productSupplier.getCost(),
                "not available"
        );
    }


    public ProductPriceSupplierManagerDTO fromEntityToExtendedSupplierDTO(ProductSupplier productSupplier, Double dolar_price)
    {
        return new ProductPriceSupplierManagerDTO(
                productSupplier.getIdProductSupplier(),
                productSupplier.getSupplier().getIdSupplier(),
                productSupplier.getSupplier().getCompanyName(),
                productSupplier.getCost(),
                productSupplier.getCost()/dolar_price
        );
    }

    public ProductPriceSupplierManagerDTONoDollarPrice fromEntityToExtendedSupplierDTODolarless(ProductSupplier productSupplier)
    {
        return new ProductPriceSupplierManagerDTONoDollarPrice(
                productSupplier.getIdProductSupplier(),
                productSupplier.getSupplier().getIdSupplier(),
                productSupplier.getSupplier().getCompanyName(),
                productSupplier.getCost(),
                "not available"
        );
    }
}

