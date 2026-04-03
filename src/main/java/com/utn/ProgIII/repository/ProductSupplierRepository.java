package com.utn.ProgIII.repository;

import com.utn.ProgIII.dto.*;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.model.Supplier.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long> {

//    todo: todos los metodos que necesitan el precio con el precio del dolar van a quedar raros, por que ahora necesitamos extraer el precio de otro lado

    @Query("SELECT NEW com.utn.ProgIII.dto.ExtendedProductManagerDTO(" +
            "ps.idProductSupplier,p.idProduct, p.name, ps.cost, ps.cost/:dollarPrice) " +
            "FROM ProductSupplier ps JOIN ps.product p " +
            "WHERE ps.supplier.idSupplier = :idSupplier")
    Page<ExtendedProductManagerDTO> productsBySupplierManager(Pageable pageable, @Param("idSupplier") Long idSupplier, @Param("dollarPrice") BigDecimal dollarPrice);

    @Query("SELECT NEW com.utn.ProgIII.dto.ExtendedProductManagerDTONoDollarPrice(" +
            "ps.idProductSupplier,p.idProduct, p.name, ps.cost, 'not available') " +
            "FROM ProductSupplier ps JOIN ps.product p " +
            "WHERE ps.supplier.idSupplier = :idSupplier")
    Page<ExtendedProductManagerDTONoDollarPrice> productsBySupplierManagerFallback(Pageable pageable, @Param("idSupplier") Long idSupplier);

    @Query("SELECT NEW com.utn.ProgIII.dto.ExtendedProductEmployeeDTO(" +
            "p.idProduct, p.name) " +
            "FROM ProductSupplier ps JOIN ps.product p " +
            "WHERE ps.supplier.idSupplier = :idSupplier")
    Page<ExtendedProductEmployeeDTO> productsBySupplierEmployee(Pageable pageable, @Param("idSupplier") Long idSupplier);



    @Query("SELECT NEW com.utn.ProgIII.dto.ProductPriceSupplierManagerDTO(" +
            "ps.idProductSupplier,s.idSupplier, s.companyName, ps.cost, ps.cost/:dollarPrice) " +
            "FROM ProductSupplier ps JOIN ps.supplier s " +
            "WHERE ps.product.idProduct = :idProduct")
    Page<ProductPriceSupplierManagerDTO> listPricesByProductManager(Pageable pageable, @Param("idProduct") Long idProduct, @Param("dollarPrice") BigDecimal dollarPrice);

    @Query("SELECT NEW com.utn.ProgIII.dto.ProductPriceSupplierManagerDTONoDollarPrice(" +
            "ps.idProductSupplier,s.idSupplier, s.companyName, ps.cost, 'not available') " +
            "FROM ProductSupplier ps JOIN ps.supplier s " +
            "WHERE ps.product.idProduct = :idProduct")
    Page<ProductPriceSupplierManagerDTONoDollarPrice> listPricesByProductManagerFallback(Pageable pageable,@Param("idProduct") Long idProduct);

    @Query("SELECT NEW com.utn.ProgIII.dto.ProductPriceSupplierEmployeeDTO(" +
            "s.idSupplier, s.companyName) " +
            "FROM ProductSupplier ps JOIN ps.supplier s " +
            "WHERE ps.product.idProduct = :idProduct")
    Page<ProductPriceSupplierEmployeeDTO> listPricesByProductEmployee(Pageable pageable, @Param("idProduct") Long idProduct);

    boolean existsByProductAndSupplier(Product product, Supplier supplier);

    ProductSupplier getByProductAndSupplier(Product product, Supplier supplier);

    void removeAllByProduct_IdProduct(Long productIdProduct);
}
