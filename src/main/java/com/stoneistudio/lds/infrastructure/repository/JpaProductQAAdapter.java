package com.stoneistudio.lds.infrastructure.repository;

import com.stoneistudio.lds.application.port.out.ProductQAOutputPort;
import com.stoneistudio.lds.domain.productqa.entity.ProductQA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaProductQAAdapter implements ProductQAOutputPort {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductQA save(ProductQA productQA) {
        if (productQA.getId() == null) {
            entityManager.persist(productQA);
        } else {
            productQA = entityManager.merge(productQA);
        }
        return productQA;
    }

    @Override
    public ProductQA findById(Long qaId) {
        return entityManager.find(ProductQA.class, qaId);
    }

    @Override
    public List<ProductQA> findAllByProductId(Long productId) {
        return entityManager.createQuery("SELECT qa FROM ProductQA qa WHERE qa.product.id = :productId", ProductQA.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    public void delete(ProductQA productQA) {
        entityManager.remove(productQA);
    }
}