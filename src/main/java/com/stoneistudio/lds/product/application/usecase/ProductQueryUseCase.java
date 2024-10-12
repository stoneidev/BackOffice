package com.stoneistudio.lds.product.application.usecase;

import com.stoneistudio.lds.product.application.dto.ProductDetailDTO;

public interface ProductQueryUseCase {
    ProductDetailDTO getProductDetails(Long productId);
}
