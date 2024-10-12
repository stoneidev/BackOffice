package com.stoneistudio.lds.product.framework.adapters.output;

import com.stoneistudio.lds.product.application.port.out.ProductOutputPort;
import com.stoneistudio.lds.product.domain.product.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaProductAdapter implements ProductOutputPort {

    @PersistenceContext
    private EntityManager entityManager;

    // 기존 메서드들...

    @Override
    public List<Product> findAllByCategoryId(Long categoryId) {
        if (categoryId == null) {
            return entityManager.createQuery("SELECT p FROM Product p WHERE p.categoryId IS NULL", Product.class)
                    .getResultList();
        } else {
            return entityManager.createQuery("SELECT p FROM Product p WHERE p.categoryId = :categoryId", Product.class)
                    .setParameter("categoryId", categoryId)
                    .getResultList();
        }
    }

    @Override
    public void save(Product product) {
        if (product.getProductId() == null) {
            entityManager.persist(product);
        } else {
            entityManager.merge(product);
        }

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
        entityManager.remove(
                entityManager.contains(existingProduct) ? existingProduct : entityManager.merge(existingProduct));
    }

    @Override
    public void saveAll(List<Product> products) {
        for (Product product : products) {
            save(product);
        }
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.categoryId = :categoryId", Product.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }
}
