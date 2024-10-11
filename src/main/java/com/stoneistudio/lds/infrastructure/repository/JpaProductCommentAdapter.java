package com.stoneistudio.lds.infrastructure.repository;

import com.stoneistudio.lds.application.port.out.ProductCommentOutputPort;
import com.stoneistudio.lds.domain.productcomment.entity.ProductComment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaProductCommentAdapter implements ProductCommentOutputPort {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductComment save(ProductComment productComment) {
        if (productComment.getId() == null) {
            entityManager.persist(productComment);
        } else {
            productComment = entityManager.merge(productComment);
        }
        return productComment;
    }

    @Override
    public ProductComment findById(Long commentId) {
        return entityManager.find(ProductComment.class, commentId);
    }

    @Override
    public List<ProductComment> findAllByProductId(Long productId) {
        return entityManager.createQuery("SELECT pc FROM ProductComment pc WHERE pc.productId = :productId", ProductComment.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    public void delete(ProductComment productComment) {
        entityManager.remove(productComment);
    }
}