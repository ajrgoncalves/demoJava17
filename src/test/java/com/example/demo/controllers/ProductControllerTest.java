package com.example.demo.controllers;

import com.example.demo.modelsDto.CategoryDto;
import com.example.demo.modelsDto.ProductDto;
import com.example.demo.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void getAllProducts() throws Exception {
// Arrange
        CategoryDto categoryDto = new CategoryDto(1, "category name", "category description");
        ProductDto productDto1 = new ProductDto(1L, "Product 1", "Description 1", 10.0, categoryDto.getId());
        ProductDto productDto2 = new ProductDto(2L, "Product 2", "Description 2", 20.0, categoryDto.getId());
        when(productService.getAllProducts()).thenReturn(List.of(productDto1, productDto2));

        // Act & Assert
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].name").value("Product 2"));
    }

    @Test
    void getProductById() throws Exception {
        // Arrange
        CategoryDto categoryDto = new CategoryDto(1, "category name", "category description");
        ProductDto productDto = new ProductDto(1L, "Product 1", "Description 1", 10.0, categoryDto.getId());
        when(productService.getProductById(1L)).thenReturn(productDto);

        // Act & Assert
        mockMvc.perform(get("/api/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.description").value("Description 1"));
    }

    @Test
    void createProduct() throws Exception {
        // Arrange
        CategoryDto categoryDto = new CategoryDto(1, "category name", "category description");
        ProductDto productDto = new ProductDto(1L, "Product 1", "Description 1", 10.0, categoryDto.getId());
        when(productService.createProduct(any(ProductDto.class))).thenReturn(productDto);

        // Act & Assert
        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content("{\"name\":\"Product 1\",\"description\":\"Description 1\",\"price\":10.0, \"categoryId\":1}"))
                .andExpect(status().isCreated())  // Ensure it expects 201 Created
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.description").value("Description 1"));
    }

    @Test
    void updateProduct() throws Exception {
        // Arrange
        CategoryDto categoryDto = new CategoryDto(1, "category name", "category description");
        ProductDto productDto = new ProductDto(1L, "Updated Product", "Updated Description", 15.0, categoryDto.getId());

        // Mocking the service method with the correct ProductDto values
        when(productService.updateProduct(1L, productDto)).thenReturn(productDto);

        // Act & Assert
        mockMvc.perform(put("/api/products/{id}", 1L)
                        .contentType("application/json")
                        .content("{\"id\": 1, \"name\":\"Updated Product\",\"description\":\"Updated Description\",\"price\":15.0, \"categoryId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    void deleteProduct() throws Exception {
        // Arrange
        doNothing().when(productService).deleteProduct(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/products/{id}", 1L))
                .andExpect(status().isNoContent());

        // Verify the delete call
        verify(productService, times(1)).deleteProduct(1L);
    }
}