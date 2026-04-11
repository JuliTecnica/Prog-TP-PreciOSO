package com.utn.ProgIII.validations;

import com.utn.ProgIII.exceptions.ConflictException;
import com.utn.ProgIII.model.Product.Category;
import com.utn.ProgIII.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidations {
    @Autowired
    CategoryRepository categoryRepository;


    public void checkIfCategoryNameExists(Category category)
    {
        if(categoryRepository.existsByName(category.getName()))
        {
            throw new ConflictException("Esa categoria ya existe!");
        }
    }

}
