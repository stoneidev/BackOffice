package com.stoneistudio.lds.application.port.in;

import com.stoneistudio.lds.application.port.out.CategoryOutputPort;
import com.stoneistudio.lds.application.port.out.ProductOutputPort;
import com.stoneistudio.lds.application.usecase.CategoryUseCase;
import com.stoneistudio.lds.domain.category.entity.Category;
import com.stoneistudio.lds.domain.product.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryInputPort implements CategoryUseCase {
    private final CategoryOutputPort categoryOutputPort;
    private final ProductOutputPort productOutputPort;

    public CategoryInputPort(CategoryOutputPort categoryOutputPort, ProductOutputPort productOutputPort) {
        this.categoryOutputPort = categoryOutputPort;
        this.productOutputPort = productOutputPort;
    }

    @Override
    public Category createCategory(String name, Long parentId) {
        var category = new Category(name);
        if (parentId != null) {
            var parent = categoryOutputPort.findById(parentId);
            if (parent == null) {
                throw new IllegalArgumentException("상위 카테고리가 존재하지 않습니다.");
            }
            parent.addChild(category);
        }
        return categoryOutputPort.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        var category = categoryOutputPort.findById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("카테고리가 존재하지 않습니다.");
        }
        try {
            category.getChildren().forEach(child -> {
                child.getProducts().forEach(product -> product.setCategory(null));
                productOutputPort.saveAll(child.getProducts());
            });
            category.getProducts().forEach(product -> product.setCategory(null));
            productOutputPort.saveAll(category.getProducts());

            categoryOutputPort.delete(category);
        } catch (Exception e) {
            throw new RuntimeException("카테고리 삭제 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        var category = categoryOutputPort.findById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("카테고리가 존재하지 않습니다.");
        }
        category.getChildren().size();
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryOutputPort.findAll();
    }

    @Override
    public void addProductToCategory(Long productId, Long categoryId) {
        var product = productOutputPort.findById(productId);
        var category = categoryOutputPort.findById(categoryId);
        if (product == null || category == null) {
            throw new IllegalArgumentException("제품 또는 카테고리가 존재하지 않습니다.");
        }
        product.setCategory(category);
        productOutputPort.save(product);
    }

    @Override
    public void removeProductFromCategory(Long productId) {
        var product = productOutputPort.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("제품이 존재하지 않습니다.");
        }
        product.setCategory(null);
        productOutputPort.save(product);
    }
}