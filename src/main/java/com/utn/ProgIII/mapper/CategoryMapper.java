package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.AddCategoryDTO;
import com.utn.ProgIII.dto.CategoryDTO;
import com.utn.ProgIII.model.Product.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(AddCategoryDTO categoryDTO)
    {
        return Category.builder()
                .name(categoryDTO.name())
                .build();
    }

    public CategoryDTO toDTO(Category category)
    {
        if(category == null) return null;
        return new CategoryDTO(category.getIdCategory(), category.getName());
    }

}
