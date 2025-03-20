package com.example.demo.services;

import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) throws ChangeSetPersister.NotFoundException {
        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        Product productToInsert = new Product();
        productToInsert.setName(product.getName());
        productToInsert.setDescription(product.getDescription());
        productToInsert.setPrice(product.getPrice());
        productToInsert.setCategory(category);
        return productRepository.save(productToInsert);
    }

    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.toBuilder().build();
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
