package com.stoneistudio.lds.application.port.in;

import com.stoneistudio.lds.application.usecase.ProductQAUseCase;
import com.stoneistudio.lds.domain.productqa.entity.ProductQA;
import com.stoneistudio.lds.application.port.out.ProductQAOutputPort;
import com.stoneistudio.lds.application.port.out.ProductOutputPort;
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
        var productQA = product.addProductQA(question);
        return productQAOutputPort.save(productQA);
    }

    @Override
    public ProductQA getProductQA(Long productId, Long qaId) {
        var product = productOutputPort.findById(productId);
        return product.getProductQA(qaId);
    }

    @Override
    public List<ProductQA> getAllProductQAs(Long productId) {
        var product = productOutputPort.findById(productId);
        return product.getAllProductQAs();
    }

    @Override
    public void updateProductQA(Long productId, Long qaId, String newQuestion) {
        var product = productOutputPort.findById(productId);
        product.updateProductQA(qaId, newQuestion);
        productOutputPort.save(product);
    }

    @Override
    public void answerProductQA(Long productId, Long qaId, String answer) {
        var product = productOutputPort.findById(productId);
        product.answerProductQA(qaId, answer);
        productOutputPort.save(product);
    }

    @Override
    public void deleteProductQA(Long productId, Long qaId) {
        var product = productOutputPort.findById(productId);
        product.deleteProductQA(qaId);
        productOutputPort.save(product);
    }
}