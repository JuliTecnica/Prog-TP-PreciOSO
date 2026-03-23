package com.utn.ProgIII.mapper;


import com.utn.ProgIII.model.Address.Address;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.dto.ResponseProductSupplierDTO;
import com.utn.ProgIII.model.Supplier.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductSupplierMapperTest {

    ProductSupplierMapper productSupplierMapper;
    ProductSupplier productSupplier;
    Supplier supplier;
    Product product;
    List<ProductSupplier> productSupplierList;


    @BeforeEach
    void setUp(){

        productSupplierMapper = new ProductSupplierMapper();
        productSupplier = new ProductSupplier();
        productSupplierList = new ArrayList<>();

        supplier = new Supplier(
                1L,
                "ElMayorista",
                "32121241249",
                "2236235214",
                "123@123.com",
                new Address(
                        "Alguna",
                        "123",
                        "falsa"),
                productSupplierList);

        product = new Product(1L, "Pororo", productSupplierList, ProductStatus.ENABLED);

    }

    @Test
    void fromEntityToDto_mustReturnDTO_whenReceiveAnEntity(){
        BigDecimal cost =  new BigDecimal("3.2");

        productSupplier = new ProductSupplier(
                supplier,
                product,
                cost);

        productSupplier.setIdProductSupplier(1L);

        ResponseProductSupplierDTO responseProductSupplierDTO = productSupplierMapper.fromEntityToDto(productSupplier);

        Assertions.assertEquals(responseProductSupplierDTO.idProductSupplier(), productSupplier.getIdProductSupplier());
        assertEquals(responseProductSupplierDTO.idProduct(), product.getIdProduct());
        assertEquals(responseProductSupplierDTO.idSupplier(), supplier.getIdSupplier());
        assertEquals(responseProductSupplierDTO.productName(), product.getName());
        assertEquals(responseProductSupplierDTO.supplierName(), supplier.getCompanyName());
        assertEquals(responseProductSupplierDTO.cost(), productSupplier.getCost());

    }


}
