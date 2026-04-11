package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.CreateProductDTO;
import com.utn.ProgIII.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductDTO getProductById(Long id);
    List<ProductDTO> getAllActiveProductAsList();
    List<ProductDTO> getAllProductsAsList();
    ProductDTO createProductDto (CreateProductDTO prductoDto);
    ProductDTO updateProduct (Long id, CreateProductDTO productDto);
    void deleteProduct (Long id);
    Page<ProductDTO> getProductsPage(Pageable pageable, String name, String status, List<Long> categories);
}
