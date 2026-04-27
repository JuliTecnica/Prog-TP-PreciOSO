package com.utn.ProgIII.repository;

import com.utn.ProgIII.dto.*;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.model.Supplier.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long>, QuerydslPredicateExecutor<ProductSupplier> {
    boolean existsByProductAndSupplier(Product product, Supplier supplier);

    ProductSupplier getByProductAndSupplier(Product product, Supplier supplier);

    void removeAllByProduct_IdProduct(Long productIdProduct);

    @NativeQuery("SELECT max(ps.cost) FROM product_supplier ps WHERE id_product = :idProduct")
    Optional<Double> findTopCostInProduct(@Param("idProduct") Long idProduct);

    List<ProductSupplier> findBySupplierIdSupplier(Long idSupplier);
}
