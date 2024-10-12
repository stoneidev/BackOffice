package com.stoneistudio.lds.product.framework.adapters.input;

import com.stoneistudio.lds.product.application.dto.ProductDetailDTO;
import com.stoneistudio.lds.product.application.usecase.ProductQueryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductQueryAdapter {

    private final ProductQueryUseCase productQueryUseCase;

    public ProductQueryAdapter(ProductQueryUseCase productQueryUseCase) {
        this.productQueryUseCase = productQueryUseCase;
    }

    @GetMapping("/{productId}/details")
    public ResponseEntity<ProductDetailDTO> getProductDetails(@PathVariable Long productId) {
        ProductDetailDTO productDetails = productQueryUseCase.getProductDetails(productId);
        if (productDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDetails);
    }
}
