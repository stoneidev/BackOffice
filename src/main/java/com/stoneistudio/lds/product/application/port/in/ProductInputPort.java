package com.stoneistudio.lds.product.application.port.in;

import com.stoneistudio.lds.product.application.port.out.ProductOutputPort;
import com.stoneistudio.lds.product.application.usecase.ProductUseCase;
import com.stoneistudio.lds.product.domain.product.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
        return product;
    }

    @Override
    public void updateProduct(Product product) {
        Product existingProduct = productOutputPort.findById(product.getProductId());
        if (existingProduct == null) {
            throw new IllegalArgumentException("업데이트할 제품이 존재하지 않습니다.");
        }
        existingProduct.setName(product.getName());
        existingProduct.setCategoryId(product.getCategoryId());
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

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productOutputPort.findAllByCategoryId(categoryId);
    }

    @Override
    public void changeProductCategory(Long productId, Long newCategoryId) {
        Product existingProduct = productOutputPort.findById(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("카테고리를 변경할 제품이 존재하지 않습니다.");
        }
        existingProduct.changeCategoryId(newCategoryId);
        productOutputPort.save(existingProduct);
    }

    @Override
    public void updateProductPrice(Long productId, Long newPrice) {
        Product existingProduct = productOutputPort.findById(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("가격을 업데이트할 제품이 존재하지 않습니다.");
        }
        existingProduct.changeProductPrice(newPrice);
        productOutputPort.save(existingProduct);
    }
}
