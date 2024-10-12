package com.stoneistudio.lds.product.framework.adapters.input.rest;

import com.stoneistudio.lds.product.application.usecase.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stoneistudio.lds.product.framework.adapters.input.ProductAdapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductAdapterTest {

    @Mock
    private ProductUseCase productUseCase;

    @InjectMocks
    private ProductAdapter productAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ... 기존 테스트 코드 ...

    @Test
    void updateProductPrice_ShouldReturnOk() {
        Long productId = 1L;
        Long newPrice = 1000L;

        doNothing().when(productUseCase).updateProductPrice(productId, newPrice);

        ResponseEntity<Void> response = productAdapter.updateProductPrice(productId, newPrice);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productUseCase, times(1)).updateProductPrice(productId, newPrice);
    }
}
