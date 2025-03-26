package com.example.demo.mappers;

import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.modelsDto.ProductDto;
import com.example.demo.services.CategoryService;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public ProductMapper(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    public ProductDto mapProductToProductDto(Product product) {
        if (product == null) return null;
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .build();
    }

    public Product mapProductDtoToProduct(ProductDto product) {
        if (product == null) return null;
        Category category = categoryMapper.mapCategoryDtoToCategory(categoryService.getCategoryById(product.getId()));

        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(category)
                .build();
    }
}
