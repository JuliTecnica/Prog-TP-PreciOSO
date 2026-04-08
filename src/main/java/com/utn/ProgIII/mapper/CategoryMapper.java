package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.CategoryDTO;
import com.utn.ProgIII.model.Product.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDTO categoryDTO)
    {
        return Category.builder()
                .name(categoryDTO.name())
                .build();
    }

    public CategoryDTO toDTO(Category category)
    {
        return new CategoryDTO(category.getIdCategory(), category.getName());
    }

}
