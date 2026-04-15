package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Product.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByName(String name);
}
