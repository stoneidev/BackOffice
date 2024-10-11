package com.stoneistudio.lds.adapters;

import com.stoneistudio.lds.application.usecase.CategoryUseCase;
import com.stoneistudio.lds.domain.category.entity.Category;
import com.stoneistudio.lds.framework.adapters.input.rest.CategoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryAdapterTest {

    @Mock
    private CategoryUseCase categoryUseCase;

    @InjectMocks
    private CategoryAdapter categoryAdapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category("전자제품");
        when(categoryUseCase.createCategory("전자제품", null)).thenReturn(category);

        ResponseEntity<Category> response = categoryAdapter.createCategory("전자제품", null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryUseCase, times(1)).createCategory("전자제품", null);
    }

    @Test
    public void testDeleteCategory() {
        Long categoryId = 1L;
        doNothing().when(categoryUseCase).deleteCategory(categoryId);

        ResponseEntity<Void> response = categoryAdapter.deleteCategory(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(categoryUseCase, times(1)).deleteCategory(categoryId);
    }

    @Test
    public void testGetCategoryById() {
        Long categoryId = 1L;
        Category category = new Category("전자제품");
        when(categoryUseCase.getCategoryById(categoryId)).thenReturn(category);

        ResponseEntity<Category> response = categoryAdapter.getCategoryById(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryUseCase, times(1)).getCategoryById(categoryId);
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = Arrays.asList(new Category("전자제품"), new Category("의류"));
        when(categoryUseCase.getAllCategories()).thenReturn(categories);

        ResponseEntity<List<Category>> response = categoryAdapter.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
        verify(categoryUseCase, times(1)).getAllCategories();
    }

    @Test
    public void testAddProductToCategory() {
        Long productId = 1L;
        Long categoryId = 1L;
        doNothing().when(categoryUseCase).addProductToCategory(productId, categoryId);

        ResponseEntity<Void> response = categoryAdapter.addProductToCategory(categoryId, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(categoryUseCase, times(1)).addProductToCategory(productId, categoryId);
    }

    @Test
    public void testRemoveProductFromCategory() {
        Long productId = 1L;
        doNothing().when(categoryUseCase).removeProductFromCategory(productId);

        ResponseEntity<Void> response = categoryAdapter.removeProductFromCategory(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(categoryUseCase, times(1)).removeProductFromCategory(productId);
    }
}