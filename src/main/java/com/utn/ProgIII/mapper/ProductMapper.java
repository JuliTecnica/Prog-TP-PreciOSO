package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.exceptions.InvalidRequestException;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

@Component
/*
 * Una clase que se dedica a convertir un DTO a un objeto y viceversa
 */
public class ProductMapper {


    /**
     * Se encarga de convertir un objeto a un dto
     * @param product El objeto
     * @return Un dto nuevo
     */
    public ProductDTO toProductDTO (Product product) {
        Long idProduct = product.getIdProduct();
        String name = product.getName();
        String status = product.getStatus().toString();
        Float profitMargin = product.getProfitMargin();
        Float stock = product.getStock();

        return new ProductDTO(idProduct,name,status, profitMargin, stock);
    }

    /**
     * Se encarga de convertir un objeto a un DTO
     * @param productDTO Un dto
     * @return Un objeto nuevo
     */
    public Product toEntity (ProductDTO productDTO){
        Product result = new Product();

        result.setName(productDTO.name());

        if(productDTO.status() != null && !EnumUtils.isValidEnum(ProductStatus.class, productDTO.status().toUpperCase()))
        {
            throw new InvalidRequestException("El estado no es válido");
        }

        result.setStatus(productDTO.status() == null ? ProductStatus.ENABLED : ProductStatus.valueOf(productDTO.status().toUpperCase()));

        result.setProfitMargin(productDTO.profitMargin());
        result.setStock(productDTO.stock());

        return result;
    }
}
