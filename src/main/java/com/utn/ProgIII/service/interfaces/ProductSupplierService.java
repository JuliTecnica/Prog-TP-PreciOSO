package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.*;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductSupplierService {

    ResponseProductSupplierDTO createProductSupplier(CreateProductSupplierDTO createProductSupplierDTO);
    ResponseProductSupplierDTO updateProductSupplier(UpdateProductSupplierDTO updateProductSupplierDTO, Long id);
    ResponseProductSupplierDTO getProductSupplier(Long id);
    void deleteProductSupplier(Long idProductSupplier);
    SupplierProductListDTO listProductsBySupplier(Pageable pageable, Long companyName, String exchange_rate);
    ProductPricesDTO listPricesByProduct(Pageable pageable, Long idProduct, String exchange_rate);
    NonAffectedProductsListDTO uploadCsv(String filepath, Long idSupplier, String mode);

    List<ProductSupplier> returnAllRelationshipsOfSupplier(Long idSupplier);
    void handlePostSupplierDelete(List<ProductSupplier> suppliers);
}
