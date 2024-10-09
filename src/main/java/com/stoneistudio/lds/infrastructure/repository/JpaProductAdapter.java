package com.stoneistudio.lds.infrastructure.repository;

import com.stoneistudio.lds.application.port.out.ProductOutputPort;
import com.stoneistudio.lds.domain.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("!test")
public class JpaProductAdapter implements ProductOutputPort {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Product product) {
        entityManager.persist(product);
    }

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
    public Product findById(Long productId) {
        return entityManager.find(Product.class, productId);
    }

    @Override
    public void delete(Product existingProduct) {
        entityManager.remove(existingProduct);
    }
}
