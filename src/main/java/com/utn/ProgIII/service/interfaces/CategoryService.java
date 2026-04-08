package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.CategoryDTO;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    void removeCategory(Long id);
    CategoryDTO modifyCategory(CategoryDTO categoryDTO, Long id);
    CategoryDTO getAllCategories();
}
