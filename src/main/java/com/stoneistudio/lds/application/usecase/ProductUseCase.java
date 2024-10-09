package com.stoneistudio.lds.application.usecase;

import com.stoneistudio.lds.domain.entity.Product;

import java.util.List;

public interface ProductUseCase {
    void addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Long productId);

    void updateProduct(Product product);

    void deleteProduct(Long id);

    Object findProductById(long l);
}
