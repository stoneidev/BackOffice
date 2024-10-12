package com.stoneistudio.lds.application.port.in;

import com.stoneistudio.lds.application.dto.ProductDetailDTO;
import com.stoneistudio.lds.application.port.out.ProductQueryOutputPort;
import com.stoneistudio.lds.application.usecase.ProductQueryUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductQueryInputPort implements ProductQueryUseCase {

    private final ProductQueryOutputPort productQueryOutputPort;

    public ProductQueryInputPort(ProductQueryOutputPort productQueryOutputPort) {
        this.productQueryOutputPort = productQueryOutputPort;
    }

    @Override
    public ProductDetailDTO getProductDetails(Long productId) {
        return productQueryOutputPort.getProductDetails(productId);
    }
}
