package com.stoneistudio.lds.framework.adapters.input.rest;

import com.stoneistudio.lds.application.dto.ProductDetailDTO;
import com.stoneistudio.lds.application.usecase.ProductQueryUseCase;
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
