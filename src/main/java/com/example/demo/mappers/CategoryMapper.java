package com.example.demo.mappers;

import com.example.demo.models.Category;
import com.example.demo.modelsDto.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto mapCategoryToCategoryDto(Category category) {
        if (category == null) return null;
        return CategoryDto.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public Category mapCategoryDtoToCategory(CategoryDto category) {
        if (category == null) return null;
        return Category.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

}
