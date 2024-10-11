package com.stoneistudio.lds.application.usecase;

import com.stoneistudio.lds.domain.productcomment.entity.ProductComment;

import java.util.List;

public interface ProductCommentUseCase {
    ProductComment addProductComment(Long productId, String content, String author);
    ProductComment getProductComment(Long commentId);
    List<ProductComment> getAllProductComments(Long productId);
    void updateProductComment(Long commentId, String content);
    void deleteProductComment(Long commentId);
}