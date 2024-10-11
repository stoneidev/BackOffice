package com.stoneistudio.lds.application.usecase;

import com.stoneistudio.lds.domain.category.entity.Category;

import java.util.List;

public interface CategoryUseCase {
    Category createCategory(String name, Long parentId);
    void deleteCategory(Long categoryId);
    Category getCategoryById(Long categoryId);
    List<Category> getAllCategories();
    void addProductToCategory(Long productId, Long categoryId);
    void removeProductFromCategory(Long productId);
}