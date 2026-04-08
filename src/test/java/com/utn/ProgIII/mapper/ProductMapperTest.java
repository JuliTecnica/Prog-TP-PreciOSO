package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.CreateProductDTO;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductMapperTest {
    private ProductMapper productMapper;
    private Product product;
    private Validator validator;


    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void productToDto_AllFieldsMappedFine() {
        product = Product.builder()
                .idProduct(1L)
                .name("product")
                .status(ProductStatus.ENABLED)
                .build();

        CreateProductDTO productDTO = productMapper.toProductDTO(product);

        assertThat(productDTO)
                .hasNoNullFieldsOrProperties()
                .satisfies( result -> {
                    assertThat(result.idProduct().equals(product.getIdProduct()));
                    assertThat(result.name().equals(product.getName()));
                    assertThat(result.status().equals(product.getStatus().toString()));
                });
    }

    @Test
    void dtoToProduct_AllFieldsMappedFine() {
        CreateProductDTO productDTO = CreateProductDTO.builder()
                .name ("product")
                .status("ENABLED")
                .build();

        Product result = productMapper.toEntity(productDTO);

        assertThat(result)
                .hasNoNullFieldsOrPropertiesExcept("idProduct", "productSuppliers")
                .satisfies(mappedEntity ->{

                    assertThat(mappedEntity.getName().equals((productDTO.name())));
                    assertThat(mappedEntity.getStatus().equals(ProductStatus.ENABLED));
                    
                });
    }

    @Test
    void dtoToProduct_NameIsNull() {

        CreateProductDTO productDTO = CreateProductDTO.builder()
                .name(null)
                .status("ENABLED")
                .build();

        Product result = productMapper.toEntity(productDTO);

        Set<ConstraintViolation<Product>> violations = validator.validate(result);
        Assertions.assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToProduct_NameIsTooShort() {
        CreateProductDTO productDTO = CreateProductDTO.builder()
                .name("ab")
                .status("ENABLED")
                .build();

        Product result = productMapper.toEntity(productDTO);

        Set<ConstraintViolation<Product>> violations = validator.validate(result);
        Assertions.assertThat(violations).hasSize(1);
    }

    @Test
    void dtoToProduct_NameIsTooLong() {
        CreateProductDTO productDTO = CreateProductDTO.builder()
                .name("a".repeat(51))
                .status("ENABLED")
                .build();

        Product result = productMapper.toEntity(productDTO);

        Set<ConstraintViolation<Product>> violations = validator.validate(result);
        Assertions.assertThat(violations).hasSize(1);
    }
}