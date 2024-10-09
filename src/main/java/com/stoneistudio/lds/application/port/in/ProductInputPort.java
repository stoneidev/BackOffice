package com.stoneistudio.lds.application.port.in;

import com.stoneistudio.lds.application.port.out.ProductOutputPort;
import com.stoneistudio.lds.application.usecase.ProductUseCase;
import com.stoneistudio.lds.domain.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // @Component 대신 @Service 사용
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
        return productOutputPort.findById(productId);
    }

    @Override
    public void updateProduct(Product product) {
        Product existingProduct = productOutputPort.findById(product.getProductId());
        if (existingProduct != null) {
            existingProduct.setProductName(product.getProductName());
            productOutputPort.save(existingProduct);
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        Product existingProduct = productOutputPort.findById(productId);
        if (existingProduct != null) {
            productOutputPort.delete(existingProduct);
        }
    }

    @Override
    public Object findProductById(long l) {
        return productOutputPort.findById(l);
    }
}
