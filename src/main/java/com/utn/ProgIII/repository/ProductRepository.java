package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    //Busca por contener todo o parte del string
    List<Product> findByNameContaining (String name,Pageable pageable);

    boolean existsByName(String name);
    Product getByName(String name);

    List<Product> findByStatus(ProductStatus status);
}
