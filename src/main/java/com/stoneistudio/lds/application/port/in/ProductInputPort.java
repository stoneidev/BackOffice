package com.stoneistudio.lds.application.port.in;

import com.stoneistudio.lds.application.port.out.ProductOutputPort;
import com.stoneistudio.lds.application.usecase.ProductUseCase;
import com.stoneistudio.lds.domain.product.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductInputPort implements ProductUseCase {
    private final ProductOutputPort productOutputPort;

    public ProductInputPort(ProductOutputPort productOutputPort) {
        this.productOutputPort = productOutputPort;
    }

    @Override
    public void addProduct(Product product) {
        productOutputPort.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productOutputPort.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        Product product = productOutputPort.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("해당 ID의 제품이 존재하지 않��니다.");
        }
        return product;
    }

    @Override
    public void updateProduct(Product product) {
        Product existingProduct = productOutputPort.findById(product.getProductId());
        if (existingProduct == null) {
            throw new IllegalArgumentException("업데이트할 제품이 존재하지 않습니다.");
        }
        existingProduct.setProductName(product.getProductName());
        existingProduct.setCategory(product.getCategory());
        productOutputPort.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product existingProduct = productOutputPort.findById(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("삭제할 제품이 존재하지 않습니다.");
        }
        productOutputPort.delete(existingProduct);
    }

    @Override
    public Product findProductById(long l) {
        return productOutputPort.findById(l);
    }
}