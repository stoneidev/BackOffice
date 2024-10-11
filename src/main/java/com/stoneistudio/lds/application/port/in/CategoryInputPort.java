package com.stoneistudio.lds.application.port.in;

import com.stoneistudio.lds.application.port.out.CategoryOutputPort;
import com.stoneistudio.lds.application.port.out.ProductOutputPort;
import com.stoneistudio.lds.application.usecase.CategoryUseCase;
import com.stoneistudio.lds.domain.category.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryInputPort implements CategoryUseCase {
    private final CategoryOutputPort categoryOutputPort;
    private final ProductOutputPort productOutputPort;
    private static final Logger logger = LoggerFactory.getLogger(CategoryInputPort.class);

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
            logger.error("카테고리 삭제 중 오류가 발생했습니다.", e);
            throw new RuntimeException("카테고리 삭제 중 오류가 발생했습니다.", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Category getCategoryById(Long categoryId) {
        var category = categoryOutputPort.findById(categoryId);
        if (category == null) {
            logger.error("카테고리가 존재하지 않습니다.");
        }
        // 지연 로딩을 위한 코드 제거
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