package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.CategoryDTO;
import com.utn.ProgIII.mapper.CategoryMapper;
import com.utn.ProgIII.model.Product.Category;
import com.utn.ProgIII.repository.CategoryRepository;
import com.utn.ProgIII.service.interfaces.CategoryService;
import com.utn.ProgIII.validations.CategoryValidations;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryValidations categoryValidations;

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        categoryValidations.checkIfCategoryNameExists(category);

        Category saved = categoryRepository.save(category);
        return categoryMapper.toDTO(saved);
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
