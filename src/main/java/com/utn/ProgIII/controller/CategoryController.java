package com.utn.ProgIII.controller;

import com.utn.ProgIII.dto.CategoryDTO;
import com.utn.ProgIII.service.interfaces.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(201).body(categoryService.addCategory(categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeCategory(@PathVariable Long id)
    {
        categoryService.removeCategory(id);
        return ResponseEntity.noContent().build();
    }
}
