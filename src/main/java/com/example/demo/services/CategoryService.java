package com.example.demo.services;

import com.example.demo.mappers.CategoryMapper;
import com.example.demo.models.Category;
import com.example.demo.modelsDto.CategoryDto;
import com.example.demo.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return (List<CategoryDto>) categories.stream().map(categoryMapper::mapCategoryToCategoryDto);
    }

    public CategoryDto getCategoryById(Long id) {
        return categoryMapper.mapCategoryToCategoryDto(Objects.requireNonNull(categoryRepository.findById(id).orElse(null)));
    }

    public CategoryDto createCategory(CategoryDto category) {
        Category newCategory = categoryMapper.mapCategoryDtoToCategory(category);
        return categoryMapper.mapCategoryToCategoryDto(categoryRepository.save(newCategory));
    }

    public CategoryDto updateCategory(Long id, CategoryDto category) {
        if (categoryRepository.existsById(id)) {
            Category mewCategory = categoryMapper.mapCategoryDtoToCategory(category).toBuilder().build();
            return categoryMapper.mapCategoryToCategoryDto(categoryRepository.save(mewCategory));
        }
        return null;
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
