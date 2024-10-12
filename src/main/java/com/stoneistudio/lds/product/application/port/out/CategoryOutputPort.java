package com.stoneistudio.lds.product.application.port.out;

import com.stoneistudio.lds.product.domain.category.entity.Category;

import java.util.List;

public interface CategoryOutputPort {
    Category save(Category category);
    void delete(Category category);
    Category findById(Long categoryId);
    List<Category> findAll();
}
