package com.stoneistudio.lds.product.framework.adapters.output;

import com.stoneistudio.lds.product.application.port.out.CategoryOutputPort;
import com.stoneistudio.lds.product.domain.category.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaCategoryAdapter implements CategoryOutputPort {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Category save(Category category) {
        if (category.getCategoryId() == null) {
            entityManager.persist(category);
        } else {
            category = entityManager.merge(category);
        }
        return category;
    }

    @Override
    public void delete(Category category) {
        entityManager.remove(category);
    }

    @Override
    public Category findById(Long categoryId) {
        return entityManager.find(Category.class, categoryId);
    }

    @Override
    public List<Category> findAll() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }
}
