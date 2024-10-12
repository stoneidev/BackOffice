package com.stoneistudio.lds.product.application.usecase;

import com.stoneistudio.lds.product.domain.category.entity.Category;

import java.util.List;

public interface CategoryUseCase {
    Category createCategory(String name, Long parentId);
    void deleteCategory(Long categoryId);
    Category getCategoryById(Long categoryId);
    List<Category> getAllCategories();
    void addProductToCategory(Long productId, Long categoryId);
    void removeProductFromCategory(Long productId);
}
