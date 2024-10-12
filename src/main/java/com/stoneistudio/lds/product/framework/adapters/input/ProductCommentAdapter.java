package com.stoneistudio.lds.product.framework.adapters.input;

import com.stoneistudio.lds.product.application.usecase.ProductCommentUseCase;
import com.stoneistudio.lds.product.domain.productcomment.entity.ProductComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/comments")
public class ProductCommentAdapter {
    private final ProductCommentUseCase productCommentUseCase;

    @Autowired
    public ProductCommentAdapter(ProductCommentUseCase productCommentUseCase) {
        this.productCommentUseCase = productCommentUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductComment> addProductComment(@PathVariable Long productId,
            @RequestBody ProductCommentRequest request) {
        ProductComment productComment = productCommentUseCase.addProductComment(productId, request.getContent(),
                request.getAuthor());
        return ResponseEntity.ok(productComment);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<ProductComment> getProductComment(@PathVariable Long commentId) {
        ProductComment productComment = productCommentUseCase.getProductComment(commentId);
        return ResponseEntity.ok(productComment);
    }

    @GetMapping
    public ResponseEntity<List<ProductComment>> getAllProductComments(@PathVariable Long productId) {
        List<ProductComment> productComments = productCommentUseCase.getAllProductComments(productId);
        return ResponseEntity.ok(productComments);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateProductComment(@PathVariable Long commentId,
            @RequestBody ProductCommentRequest request) {
        productCommentUseCase.updateProductComment(commentId, request.getContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteProductComment(@PathVariable Long commentId) {
        productCommentUseCase.deleteProductComment(commentId);
        return ResponseEntity.ok().build();
    }

    public static class ProductCommentRequest {
        private String content;
        private String author;

        // 기본 생성자 추가
        public ProductCommentRequest() {}

        public ProductCommentRequest(String content, String author) {
            this.content = content;
            this.author = author;
        }

        // Getters and setters
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
