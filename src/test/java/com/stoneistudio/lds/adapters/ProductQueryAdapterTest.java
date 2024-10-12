package com.stoneistudio.lds.adapters;

import com.stoneistudio.lds.application.dto.ProductDetailDTO;
import com.stoneistudio.lds.application.usecase.ProductQueryUseCase;
import com.stoneistudio.lds.framework.adapters.input.rest.ProductQueryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ProductQueryAdapterTest {

    @Mock
    private ProductQueryUseCase productQueryUseCase;

    private ProductQueryAdapter productQueryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productQueryAdapter = new ProductQueryAdapter(productQueryUseCase);
    }

    @Test
    void testGetProductDetails_Success() {
        Long productId = 1L;
        ProductDetailDTO mockDto = new ProductDetailDTO();
        mockDto.setProductId(productId);
        mockDto.setProductName("Test Product");
        mockDto.setCategoryName("Test Category");
        mockDto.setComments(Arrays.asList("Comment 1", "Comment 2"));
        mockDto.setQaItems(Arrays.asList(
            new ProductDetailDTO.QAItem("Question 1", "Answer 1"),
            new ProductDetailDTO.QAItem("Question 2", "Answer 2")
        ));

        when(productQueryUseCase.getProductDetails(productId)).thenReturn(mockDto);

        ResponseEntity<ProductDetailDTO> response = productQueryAdapter.getProductDetails(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockDto, response.getBody());
        verify(productQueryUseCase, times(1)).getProductDetails(productId);
    }

    @Test
    void testGetProductDetails_NotFound() {
        Long productId = 1L;
        when(productQueryUseCase.getProductDetails(productId)).thenReturn(null);

        ResponseEntity<ProductDetailDTO> response = productQueryAdapter.getProductDetails(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(productQueryUseCase, times(1)).getProductDetails(productId);
    }
}
