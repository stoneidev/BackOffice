package com.stoneistudio.lds.product.application.port.in;

import com.stoneistudio.lds.product.application.usecase.ProductQAUseCase;
import com.stoneistudio.lds.product.domain.productqa.entity.ProductQA;
import com.stoneistudio.lds.product.application.port.out.ProductQAOutputPort;
import com.stoneistudio.lds.product.application.port.out.ProductOutputPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductQAInputPort implements ProductQAUseCase {
    private final ProductQAOutputPort productQAOutputPort;
    private final ProductOutputPort productOutputPort;

    public ProductQAInputPort(ProductQAOutputPort productQAOutputPort, ProductOutputPort productOutputPort) {
        this.productQAOutputPort = productQAOutputPort;
        this.productOutputPort = productOutputPort;
    }

    @Override
    public ProductQA addProductQA(Long productId, String question) {
        var product = productOutputPort.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("제품을 찾을 수 없습니다.");
        }
        var productQA = new ProductQA(productId, question);
        return productQAOutputPort.save(productQA);
    }

    @Override
    public ProductQA getProductQA(Long productId, Long qaId) {
        var productQA = productQAOutputPort.findById(qaId);
        if (productQA == null || !productQA.getProductId().equals(productId)) {
            throw new IllegalArgumentException("해당 제품의 QA를 찾을 수 없습니다.");
        }
        return productQA;
    }

    @Override
    public List<ProductQA> getAllProductQAs(Long productId) {
        return productQAOutputPort.findAllByProductId(productId);
    }

    @Override
    public void updateProductQA(Long productId, Long qaId, String newQuestion) {
        var productQA = getProductQA(productId, qaId);
        productQA.setQuestion(newQuestion);
        productQAOutputPort.save(productQA);
    }

    @Override
    public void answerProductQA(Long productId, Long qaId, String answer) {
        var productQA = getProductQA(productId, qaId);
        productQA.setAnswer(answer);
        productQAOutputPort.save(productQA);
    }

    @Override
    public void deleteProductQA(Long productId, Long qaId) {
        var productQA = getProductQA(productId, qaId);
        productQAOutputPort.delete(productQA);
    }
}
