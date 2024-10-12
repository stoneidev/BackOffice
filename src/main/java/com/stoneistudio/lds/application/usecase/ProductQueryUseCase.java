package com.stoneistudio.lds.application.usecase;

import com.stoneistudio.lds.application.dto.ProductDetailDTO;

public interface ProductQueryUseCase {
    ProductDetailDTO getProductDetails(Long productId);
}
