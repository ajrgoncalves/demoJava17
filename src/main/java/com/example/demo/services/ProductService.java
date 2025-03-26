package com.example.demo.services;

import com.example.demo.mappers.ProductMapper;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.modelsDto.ProductDto;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return (List<ProductDto>) products.stream().map(productMapper::mapProductToProductDto);
    }

    public ProductDto getProductById(Long id) {
        return productMapper.mapProductToProductDto(Objects.requireNonNull(productRepository.findById(id).orElse(null)));
    }

    public ProductDto createProduct(ProductDto product) throws ChangeSetPersister.NotFoundException {
        Category category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        Product productToInsert = new Product();
        productToInsert.setName(product.getName());
        productToInsert.setDescription(product.getDescription());
        productToInsert.setPrice(product.getPrice());
        productToInsert.setCategory(category);
        return productMapper.mapProductToProductDto(productRepository.save(productToInsert));
    }

    public ProductDto updateProduct(Long id, ProductDto product) {
        if (productRepository.existsById(id)) {
            Product newProduct = productMapper.mapProductDtoToProduct(product).toBuilder().build();
            return productMapper.mapProductToProductDto(newProduct);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
