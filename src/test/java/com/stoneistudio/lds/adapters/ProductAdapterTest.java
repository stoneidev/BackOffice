package com.stoneistudio.lds.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stoneistudio.lds.application.usecase.ProductUseCase;
import com.stoneistudio.lds.domain.entity.Product;
import com.stoneistudio.lds.framework.adapters.input.rest.ProductAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductAdapterTest {

    private MockMvc mockMvc;

    @Mock
    private ProductUseCase productUseCase;

    private ProductAdapter productAdapter;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productAdapter = new ProductAdapter(productUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(productAdapter).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddProduct() throws Exception {
        Product product = new Product("New Product");

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());

        verify(productUseCase, times(1)).addProduct(any(Product.class));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product("Product 1"),
                new Product("Product 2"));
        when(productUseCase.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productName.name").value("Product 1"))
                .andExpect(jsonPath("$[1].productName.name").value("Product 2"));

        verify(productUseCase, times(1)).getAllProducts();
    }

    @Test
    public void testGetProductById() throws Exception {
        Long productId = 1L;
        Product product = new Product("Existing Product");
        product.setProductId(productId);

        when(productUseCase.getProductById(productId)).thenReturn(product);

        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName.name").value("Existing Product"));

        verify(productUseCase, times(1)).getProductById(productId);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Long productId = 1L;
        Product product = new Product("Updated Product");
        product.setProductId(productId);

        mockMvc.perform(put("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());

        verify(productUseCase, times(1)).updateProduct(any(Product.class));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isOk());

        verify(productUseCase, times(1)).deleteProduct(productId);
    }
}
