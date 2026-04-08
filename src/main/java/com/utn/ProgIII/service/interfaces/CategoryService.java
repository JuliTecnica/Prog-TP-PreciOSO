package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.AddCategoryDTO;
import com.utn.ProgIII.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(AddCategoryDTO categoryDTO);
    void removeCategory(Long id);
    CategoryDTO modifyCategory(AddCategoryDTO categoryDTO, Long id);
    List<CategoryDTO> getAllCategories();
}
