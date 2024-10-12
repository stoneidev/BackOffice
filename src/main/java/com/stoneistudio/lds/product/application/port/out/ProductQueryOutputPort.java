package com.stoneistudio.lds.product.application.port.out;

import com.stoneistudio.lds.product.application.dto.ProductDetailDTO;

public interface ProductQueryOutputPort {
    ProductDetailDTO getProductDetails(Long productId);
}
