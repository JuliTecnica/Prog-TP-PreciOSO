package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.model.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long>, QuerydslPredicateExecutor<Supplier> {

    boolean existsByCuit(String cuit);
    boolean existsByCompanyName(String companyName);

}
