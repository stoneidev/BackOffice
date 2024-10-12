package com.stoneistudio.lds.product.application.usecase;

import com.stoneistudio.lds.product.domain.product.entity.Product;
import com.stoneistudio.lds.product.domain.productcomment.entity.ProductComment;

import java.util.List;

public interface ProductCommentUseCase {
    ProductComment addProductComment(Long productId, String content, String author);
    ProductComment getProductComment(Long commentId);
    List<ProductComment> getAllProductComments(Long productId);
    void updateProductComment(Long commentId, String content);
    void deleteProductComment(Long commentId);

    interface ProductUseCase {
        void addProduct(Product product);

        List<Product> getAllProducts();

        Product getProductById(Long productId);

        void updateProduct(Product product);

        void deleteProduct(Long id);

        Object findProductById(long l);

        List<Product> getProductsByCategory(Long categoryId);

        void changeProductCategory(Long productId, Long newCategoryId);

        void updateProductPrice(Long id, Long newPrice);
    }
}
