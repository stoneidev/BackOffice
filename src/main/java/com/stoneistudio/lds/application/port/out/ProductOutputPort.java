package com.stoneistudio.lds.application.port.out;

import com.stoneistudio.lds.domain.product.entity.Product;

import java.util.List;

public interface ProductOutputPort {
    void save(Product product);

    List<Product> findAll();

    Product findById(Long productId);

    void delete(Product existingProduct);

    void saveAll(List<Product> products);

    List<Product> findAllByCategoryId(Long categoryId);
}
