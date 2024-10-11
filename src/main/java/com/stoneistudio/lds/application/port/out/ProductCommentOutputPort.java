package com.stoneistudio.lds.application.port.out;

import com.stoneistudio.lds.domain.productcomment.entity.ProductComment;

import java.util.List;

public interface ProductCommentOutputPort {
    ProductComment save(ProductComment productComment);
    ProductComment findById(Long commentId);
    List<ProductComment> findAllByProductId(Long productId);
    void delete(ProductComment productComment);
}