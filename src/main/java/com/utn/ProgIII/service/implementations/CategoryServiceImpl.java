package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.CategoryDTO;
import com.utn.ProgIII.repository.CategoryRepository;
import com.utn.ProgIII.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {

    }

    @Override
    public void removeCategory(Long id) {

    }

    @Override
    public CategoryDTO modifyCategory(CategoryDTO categoryDTO, Long id) {

    }

    @Override
    public CategoryDTO getAllCategories() {

    }
}
