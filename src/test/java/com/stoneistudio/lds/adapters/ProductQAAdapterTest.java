package com.stoneistudio.lds.adapters;

import com.stoneistudio.lds.application.usecase.ProductQAUseCase;
import com.stoneistudio.lds.domain.productqa.entity.ProductQA;
import com.stoneistudio.lds.framework.adapters.input.rest.ProductQAAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductQAAdapterTest {

    @Mock
    private ProductQAUseCase productQAUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ProductQAAdapter productQAAdapter = new ProductQAAdapter(productQAUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(productQAAdapter).build();
    }

    @Test
    void testAddProductQA() throws Exception {
        Long productId = 1L;
        String question = "How to use this product?";
        ProductQA productQA = new ProductQA(productId, question);
        productQA.setQaId(1L);

        when(productQAUseCase.addProductQA(eq(productId), eq(question))).thenReturn(productQA);

        mockMvc.perform(post("/api/products/{productId}/qa", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(question))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qaId").value(1L))
                .andExpect(jsonPath("$.question").value(question));

        verify(productQAUseCase).addProductQA(productId, question);
    }

    @Test
    void testGetProductQA() throws Exception {
        Long productId = 1L;
        Long qaId = 1L;
        ProductQA productQA = new ProductQA(productId, "How to use this product?");
        productQA.setQaId(qaId);

        when(productQAUseCase.getProductQA(productId, qaId)).thenReturn(productQA);

        mockMvc.perform(get("/api/products/{productId}/qa/{qaId}", productId, qaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qaId").value(qaId))
                .andExpect(jsonPath("$.question").value("How to use this product?"));

        verify(productQAUseCase).getProductQA(productId, qaId);
    }

    @Test
    void testGetAllProductQAs() throws Exception {
        Long productId = 1L;
        ProductQA qa1 = new ProductQA(productId, "Question 1");
        qa1.setQaId(1L);
        ProductQA qa2 = new ProductQA(productId, "Question 2");
        qa2.setQaId(2L);

        when(productQAUseCase.getAllProductQAs(productId)).thenReturn(Arrays.asList(qa1, qa2));

        mockMvc.perform(get("/api/products/{productId}/qa", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].qaId").value(1L))
                .andExpect(jsonPath("$[0].question").value("Question 1"))
                .andExpect(jsonPath("$[1].qaId").value(2L))
                .andExpect(jsonPath("$[1].question").value("Question 2"));

        verify(productQAUseCase).getAllProductQAs(productId);
    }
}
