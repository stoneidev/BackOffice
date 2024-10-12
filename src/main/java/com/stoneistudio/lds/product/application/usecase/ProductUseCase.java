package com.stoneistudio.lds.product.application.usecase;


import com.stoneistudio.lds.product.domain.product.entity.Product;

import java.util.List;

public interface ProductUseCase {
    void addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Long productId);

    void updateProduct(Product product);

    void deleteProduct(Long id);

    Object findProductById(long l);

    List<Product> getProductsByCategory(Long categoryId);

    void changeProductCategory(Long productId, Long newCategoryId);

    void updateProductPrice(Long productId, Long newPrice);
}
