package com.stoneistudio.lds.framework.adapters.input.rest;

import com.stoneistudio.lds.application.usecase.ProductQAUseCase;
import com.stoneistudio.lds.domain.productqa.entity.ProductQA;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/qa")
public class ProductQAAdapter {
    private final ProductQAUseCase productQAUseCase;

    public ProductQAAdapter(ProductQAUseCase productQAUseCase) {
        this.productQAUseCase = productQAUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductQA> addProductQA(@PathVariable Long productId, @RequestBody String question) {
        ProductQA productQA = productQAUseCase.addProductQA(productId, question);
        return ResponseEntity.ok(productQA);
    }

    @GetMapping("/{qaId}")
    public ResponseEntity<ProductQA> getProductQA(@PathVariable Long productId, @PathVariable Long qaId) {
        ProductQA productQA = productQAUseCase.getProductQA(productId, qaId);
        return ResponseEntity.ok(productQA);
    }

    @GetMapping
    public ResponseEntity<List<ProductQA>> getAllProductQAs(@PathVariable Long productId) {
        List<ProductQA> productQAs = productQAUseCase.getAllProductQAs(productId);
        return ResponseEntity.ok(productQAs);
    }

    @PutMapping("/{qaId}")
    public ResponseEntity<Void> updateProductQA(@PathVariable Long productId, @PathVariable Long qaId, @RequestBody String newQuestion) {
        productQAUseCase.updateProductQA(productId, qaId, newQuestion);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{qaId}/answer")
    public ResponseEntity<Void> answerProductQA(@PathVariable Long productId, @PathVariable Long qaId, @RequestBody String answer) {
        productQAUseCase.answerProductQA(productId, qaId, answer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{qaId}")
    public ResponseEntity<Void> deleteProductQA(@PathVariable Long productId, @PathVariable Long qaId) {
        productQAUseCase.deleteProductQA(productId, qaId);
        return ResponseEntity.ok().build();
    }
}