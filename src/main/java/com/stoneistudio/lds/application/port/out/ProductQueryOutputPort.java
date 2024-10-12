package com.stoneistudio.lds.application.port.out;

import com.stoneistudio.lds.application.dto.ProductDetailDTO;

public interface ProductQueryOutputPort {
    ProductDetailDTO getProductDetails(Long productId);
}
