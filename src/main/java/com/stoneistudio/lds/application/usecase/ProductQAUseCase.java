package com.stoneistudio.lds.application.usecase;

import com.stoneistudio.lds.domain.productqa.entity.ProductQA;

import java.util.List;

public interface ProductQAUseCase {
    ProductQA addProductQA(Long productId, String question);
    ProductQA getProductQA(Long productId, Long qaId);
    List<ProductQA> getAllProductQAs(Long productId);
    void updateProductQA(Long productId, Long qaId, String newQuestion);
    void answerProductQA(Long productId, Long qaId, String answer);
    void deleteProductQA(Long productId, Long qaId);
}