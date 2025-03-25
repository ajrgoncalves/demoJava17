package com.example.demo.mappers;

import com.example.demo.models.Category;
import com.example.demo.modelsDto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    public CategoryDto mapCategoryToCategoryDto(Category category){
        return CategoryDto.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public Category mapCategoryDtoToCategory(CategoryDto category){
        return Category.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

}
