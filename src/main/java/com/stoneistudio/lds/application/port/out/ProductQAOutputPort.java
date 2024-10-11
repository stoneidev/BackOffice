package com.stoneistudio.lds.application.port.out;

import com.stoneistudio.lds.domain.productqa.entity.ProductQA;

import java.util.List;

public interface ProductQAOutputPort {
    ProductQA save(ProductQA productQA);
    ProductQA findById(Long qaId);
    List<ProductQA> findAllByProductId(Long productId);
    void delete(ProductQA productQA);
}