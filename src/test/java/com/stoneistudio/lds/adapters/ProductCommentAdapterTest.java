package com.stoneistudio.lds.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stoneistudio.lds.application.usecase.ProductCommentUseCase;
import com.stoneistudio.lds.domain.productcomment.entity.ProductComment;
import com.stoneistudio.lds.framework.adapters.input.rest.ProductCommentAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductCommentAdapterTest {

    private MockMvc mockMvc;

    @Mock
    private ProductCommentUseCase productCommentUseCase;

    private ProductCommentAdapter productCommentAdapter;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productCommentAdapter = new ProductCommentAdapter(productCommentUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(productCommentAdapter).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddProductComment() throws Exception {
        Long productId = 1L;
        ProductComment productComment = new ProductComment(productId, "Great product!", "John Doe");
        when(productCommentUseCase.addProductComment(eq(productId), anyString(), anyString()))
                .thenReturn(productComment);

        mockMvc.perform(post("/api/products/{productId}/comments", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new ProductCommentAdapter.ProductCommentRequest("Great product!", "John Doe"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Great product!"))
                .andExpect(jsonPath("$.author").value("John Doe"));
    }

    @Test
    void testGetProductComment() throws Exception {
        Long productId = 1L;
        Long commentId = 1L;
        ProductComment productComment = new ProductComment(productId, "Great product!", "John Doe");
        when(productCommentUseCase.getProductComment(commentId)).thenReturn(productComment);

        mockMvc.perform(get("/api/products/{productId}/comments/{commentId}", productId, commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Great product!"))
                .andExpect(jsonPath("$.author").value("John Doe"));
    }

    @Test
    void testGetAllProductComments() throws Exception {
        Long productId = 1L;
        List<ProductComment> productComments = Arrays.asList(
                new ProductComment(productId, "Great product!", "John Doe"),
                new ProductComment(productId, "Awesome!", "Jane Doe"));
        when(productCommentUseCase.getAllProductComments(productId)).thenReturn(productComments);

        mockMvc.perform(get("/api/products/{productId}/comments", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Great product!"))
                .andExpect(jsonPath("$[0].author").value("John Doe"))
                .andExpect(jsonPath("$[1].content").value("Awesome!"))
                .andExpect(jsonPath("$[1].author").value("Jane Doe"));
    }

    @Test
    void testUpdateProductComment() throws Exception {
        Long productId = 1L;
        Long commentId = 1L;
        mockMvc.perform(put("/api/products/{productId}/comments/{commentId}", productId, commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new ProductCommentAdapter.ProductCommentRequest("Updated comment", "John Doe"))))
                .andExpect(status().isOk());

        verify(productCommentUseCase).updateProductComment(eq(commentId), eq("Updated comment"));
    }

    @Test
    void testDeleteProductComment() throws Exception {
        Long productId = 1L;
        Long commentId = 1L;
        mockMvc.perform(delete("/api/products/{productId}/comments/{commentId}", productId, commentId))
                .andExpect(status().isOk());

        verify(productCommentUseCase).deleteProductComment(commentId);
    }
}