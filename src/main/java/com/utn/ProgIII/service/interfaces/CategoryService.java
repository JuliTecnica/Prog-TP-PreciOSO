package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    void removeCategory(Long id);
    CategoryDTO modifyCategory(CategoryDTO categoryDTO, Long id);
    List<CategoryDTO> getAllCategories();
}
