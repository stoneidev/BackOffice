package com.stoneistudio.lds.framework.adapters.input.rest;

import com.stoneistudio.lds.application.usecase.ProductUseCase;
import com.stoneistudio.lds.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductAdapter {

    private final ProductUseCase productUseCase;

    @Autowired
    public ProductAdapter(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @Operation(summary = "Add a new product", description = "This endpoint adds a new product to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody Product product) {
        productUseCase.addProduct(product);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all products", description = "This endpoint retrieves all products from the system.")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productUseCase.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get product by ID", description = "This endpoint retrieves a product by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productUseCase.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update an existing product", description = "This endpoint updates an existing product.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setProductId(id); // ID를 설정하여 업데이트할 제품을 지정
        productUseCase.updateProduct(product);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a product", description = "This endpoint deletes a product by its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
