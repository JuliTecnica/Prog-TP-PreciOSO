package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Credential.Credential;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Credential,Long> {
    boolean existsByName(String name);
}
