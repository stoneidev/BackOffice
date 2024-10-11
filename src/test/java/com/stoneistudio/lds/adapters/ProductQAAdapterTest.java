package com.stoneistudio.lds.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stoneistudio.lds.application.usecase.ProductQAUseCase;
import com.stoneistudio.lds.domain.product.entity.Product;
import com.stoneistudio.lds.domain.productqa.entity.ProductQA;
import com.stoneistudio.lds.framework.adapters.input.rest.ProductQAAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductQAAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ProductQAUseCase productQAUseCase;

    private ProductQAAdapter productQAAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productQAAdapter = new ProductQAAdapter(productQAUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(productQAAdapter).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddProductQA() throws Exception {
        Long productId = 1L;
        String question = "How to use this product?";
        ProductQA productQA = new ProductQA(new Product("Test Product"), question);
        productQA.setId(1L);

        when(productQAUseCase.addProductQA(eq(productId), eq(question))).thenReturn(productQA);

        mockMvc.perform(post("/api/products/{productId}/qa", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(question))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.question").value(question));

        verify(productQAUseCase).addProductQA(productId, question);
    }

    @Test
    void testGetProductQA() throws Exception {
        Long productId = 1L;
        Long qaId = 1L;
        ProductQA productQA = new ProductQA(new Product("Test Product"), "Test Question");
        productQA.setId(qaId);

        when(productQAUseCase.getProductQA(eq(productId), eq(qaId))).thenReturn(productQA);

        mockMvc.perform(get("/api/products/{productId}/qa/{qaId}", productId, qaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(qaId))
                .andExpect(jsonPath("$.question").value("Test Question"));

        verify(productQAUseCase).getProductQA(productId, qaId);
    }

    @Test
    void testGetAllProductQAs() throws Exception {
        Long productId = 1L;
        List<ProductQA> productQAs = Arrays.asList(
                new ProductQA(new Product("Test Product"), "Question 1"),
                new ProductQA(new Product("Test Product"), "Question 2"));
        productQAs.get(0).setId(1L);
        productQAs.get(1).setId(2L);

        when(productQAUseCase.getAllProductQAs(eq(productId))).thenReturn(productQAs);

        mockMvc.perform(get("/api/products/{productId}/qa", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].question").value("Question 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].question").value("Question 2"));

        verify(productQAUseCase).getAllProductQAs(productId);
    }

    @Test
    void testUpdateProductQA() throws Exception {
        Long productId = 1L;
        Long qaId = 1L;
        String newQuestion = "Is this product durable?";

        mockMvc.perform(put("/api/products/{productId}/qa/{qaId}", productId, qaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newQuestion))
                .andExpect(status().isOk());

        verify(productQAUseCase).updateProductQA(productId, qaId, newQuestion);
    }

    @Test
    void testAnswerProductQA() throws Exception {
        Long productId = 1L;
        Long qaId = 1L;
        String answer = "Yes, it's very durable.";

        mockMvc.perform(put("/api/products/{productId}/qa/{qaId}/answer", productId, qaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(answer))
                .andExpect(status().isOk());

        verify(productQAUseCase).answerProductQA(productId, qaId, answer);
    }

    @Test
    void testDeleteProductQA() throws Exception {
        Long productId = 1L;
        Long qaId = 1L;

        mockMvc.perform(delete("/api/products/{productId}/qa/{qaId}", productId, qaId))
                .andExpect(status().isOk());

        verify(productQAUseCase).deleteProductQA(productId, qaId);
    }
}