package com.example.demo.controllers;

import com.example.demo.modelsDto.CategoryDto;
import com.example.demo.modelsDto.CategoryDto;
import com.example.demo.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getAllCategories() throws Exception {
        // Arrange
        CategoryDto categoryDto1 = new CategoryDto(1, "category1 name", "category1 description");
        CategoryDto categoryDto2 = new CategoryDto(2, "category2 name", "category2 description");

        when(categoryService.getAllCategories()).thenReturn(List.of(categoryDto1, categoryDto2));
        
        // Act & Assert
        mockMvc.perform(get("/api/categories"))
                        .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].name").value("category1 name"))
                                .andExpect(jsonPath("$[1].name").value("category2 name"));
    }

    @Test
    void getCategoryById() throws Exception{

        CategoryDto categoryDto = new CategoryDto(1, "category name", "category description");
        when(categoryService.getCategoryById(1L)).thenReturn(categoryDto);

        // Act & Assert
        mockMvc.perform(get("/api/categories/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("category name"))
                .andExpect(jsonPath("$.description").value("category description"));
    }

    @Test
    void createCategory() throws Exception{
        // Arrange
        CategoryDto categoryDto = new CategoryDto(1, "category name", "category description");
        when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(categoryDto);

        // Act & Assert
        mockMvc.perform(post("/api/categories")
                        .contentType("application/json")
                        .content("{\"name\":\"category name\",\"description\":\"category description\"}"))
                .andExpect(status().isCreated())  // Ensure it expects 201 Created
                .andExpect(jsonPath("$.name").value("category name"))
                .andExpect(jsonPath("$.description").value("category description"));
    }

    @Test
    void updateCategory() throws Exception{
        // Arrange
        CategoryDto categoryDto = new CategoryDto(1L, "category name", "category description");

        when(categoryService.updateCategory(1L, categoryDto)).thenReturn(categoryDto);

        // Act & Assert
        mockMvc.perform(put("/api/categories/{id}", 1L)
                        .contentType("application/json")
                        .content("{\"id\": 1, \"name\":\"category name\",\"description\":\"category description\"}"))
                .andExpect(status().isOk()) // Ensure it expects 201 Created
                .andExpect(jsonPath("$.name").value("category name"))
                .andExpect(jsonPath("$.description").value("category description"));
    }

    @Test
    void deleteCategory() throws Exception{
        // Arrange
        doNothing().when(categoryService).deleteCategory(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/categories/{id}", 1L))
                .andExpect(status().isNoContent());

        // Verify the delete call
        verify(categoryService, times(1)).deleteCategory(1L);
    }
}