package com.stoneistudio.lds.infrastructure.repository;

import com.stoneistudio.lds.application.dto.ProductDetailDTO;
import com.stoneistudio.lds.application.port.out.ProductQueryOutputPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaProductQueryAdapter implements ProductQueryOutputPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductDetailDTO getProductDetails(Long productId) {
        String jpql = """
                    SELECT p.productId as productId, p.name as productName, c.name as categoryName,
                           pc.content as comment, pqa.question as question, pqa.answer as answer
                    FROM Product p
                    LEFT JOIN Category c ON p.categoryId = c.categoryId
                    LEFT JOIN ProductComment pc ON p.productId = pc.productId
                    LEFT JOIN ProductQA pqa ON p.productId = pqa.productId
                    WHERE p.productId = :productId
                """;

        List<Tuple> results = entityManager.createQuery(jpql, Tuple.class)
                .setParameter("productId", productId)
                .getResultList();

        if (results.isEmpty()) {
            return null;
        }

        Tuple firstResult = results.get(0);
        ProductDetailDTO dto = new ProductDetailDTO();
        dto.setProductId(firstResult.get("productId", Long.class)); // 필드 이름 확인
        dto.setProductName(firstResult.get("productName", String.class)); // 필드 이름 확인
        dto.setCategoryName(firstResult.get("categoryName", String.class)); // 필드 이름 확인

        dto.setComments(results.stream()
                .map(t -> t.get("comment", String.class)) // 필드 이름 확인
                .filter(comment -> comment != null)
                .distinct()
                .collect(Collectors.toList()));

        dto.setQaItems(results.stream()
                .filter(t -> t.get("question", String.class) != null) // 필드 이름 확인
                .map(t -> new ProductDetailDTO.QAItem(
                        t.get("question", String.class), // 필드 이름 확인
                        t.get("answer", String.class) // 필드 이름 확인
                ))
                .distinct()
                .collect(Collectors.toList()));

        return dto;
    }
}
