package com.stoneistudio.lds.infrastructure.repository;

import com.stoneistudio.lds.application.port.out.ProductOutputPort;
import com.stoneistudio.lds.domain.entity.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("test")
public class InMemoryProductAdapter implements ProductOutputPort {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public void save(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(idGenerator.incrementAndGet());
        }
        products.removeIf(p -> p.getProductId().equals(product.getProductId()));
        products.add(product);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public Product findById(Long productId) {
        return products.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Product existingProduct) {
        products.removeIf(p -> p.getProductId().equals(existingProduct.getProductId()));
    }
}
