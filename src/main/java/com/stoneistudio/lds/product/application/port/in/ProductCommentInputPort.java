package com.stoneistudio.lds.product.application.port.in;

import com.stoneistudio.lds.product.application.usecase.ProductCommentUseCase;
import com.stoneistudio.lds.product.domain.productcomment.entity.ProductComment;
import com.stoneistudio.lds.product.application.port.out.ProductCommentOutputPort;
import com.stoneistudio.lds.product.application.port.out.ProductOutputPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductCommentInputPort implements ProductCommentUseCase {
    private final ProductCommentOutputPort productCommentOutputPort;
    private final ProductOutputPort productOutputPort;

    public ProductCommentInputPort(ProductCommentOutputPort productCommentOutputPort,
            ProductOutputPort productOutputPort) {
        this.productCommentOutputPort = productCommentOutputPort;
        this.productOutputPort = productOutputPort;
    }

    @Override
    public ProductComment addProductComment(Long productId, String content, String author) {
        var product = productOutputPort.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        var productComment = new ProductComment(productId, content, author);
        return productCommentOutputPort.save(productComment);
    }

    @Override
    public ProductComment getProductComment(Long commentId) {
        var productComment = productCommentOutputPort.findById(commentId);
        return productComment;
    }

    @Override
    public List<ProductComment> getAllProductComments(Long productId) {
        return productCommentOutputPort.findAllByProductId(productId);
    }

    @Override
    public void updateProductComment(Long commentId, String content) {
        var productComment = productCommentOutputPort.findById(commentId);
        productComment.setContent(content);
        productCommentOutputPort.save(productComment);
    }

    @Override
    public void deleteProductComment(Long commentId) {
        var productComment = productCommentOutputPort.findById(commentId);
        productCommentOutputPort.delete(productComment);
    }
}