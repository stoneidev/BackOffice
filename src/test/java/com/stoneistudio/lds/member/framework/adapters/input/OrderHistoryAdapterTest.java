package com.stoneistudio.lds.member.framework.adapters.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stoneistudio.lds.member.application.usecase.OrderHistoryUseCase;
import com.stoneistudio.lds.member.domain.orderHistory.entity.OrderHistory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;


import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderHistoryAdapterTest {

    private MockMvc mockMvc;

    @Mock
    private OrderHistoryUseCase orderHistoryUseCase;

    private OrderHistoryAdapter orderHistoryAdapter;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderHistoryAdapter = new OrderHistoryAdapter(orderHistoryUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(orderHistoryAdapter).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("주문 내역 생성")
    void createOrderHistory() throws Exception {
        OrderHistory orderHistory = new OrderHistory(1L, 1L, "PENDING");
        given(orderHistoryUseCase.createOrderHistory(anyLong(), anyLong(), anyString())).willReturn(orderHistory);

        mockMvc.perform(post("/api/order-histories")
                .param("memberId", "1")
                .param("orderId", "1")
                .param("orderStatus", "PENDING")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(1))
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.orderStatus").value("PENDING"));
    }

    @Test
    @DisplayName("주문 내역 조회")
    void getOrderHistoryById() throws Exception {
        OrderHistory orderHistory = new OrderHistory(1L, 1L, "COMPLETED");
        given(orderHistoryUseCase.getOrderHistoryById(anyLong())).willReturn(orderHistory);

        mockMvc.perform(get("/api/order-histories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(1))
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.orderStatus").value("COMPLETED"));
    }

    @Test
    @DisplayName("회원별 주문 내역 조회")
    void getOrderHistoriesByMemberId() throws Exception {
        List<OrderHistory> orderHistories = Arrays.asList(
                new OrderHistory(1L, 1L, "PENDING"),
                new OrderHistory(1L, 2L, "COMPLETED"));
        given(orderHistoryUseCase.getOrderHistoriesByMemberId(anyLong())).willReturn(orderHistories);

        mockMvc.perform(get("/api/order-histories/member/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberId").value(1))
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].orderStatus").value("PENDING"))
                .andExpect(jsonPath("$[1].memberId").value(1))
                .andExpect(jsonPath("$[1].orderId").value(2))
                .andExpect(jsonPath("$[1].orderStatus").value("COMPLETED"));
    }

    @Test
    @DisplayName("주문 내역 수정")
    void updateOrderHistory() throws Exception {
        OrderHistory updatedOrderHistory = new OrderHistory(1L, 1L, "SHIPPED");
        given(orderHistoryUseCase.updateOrderHistory(anyLong(), anyString())).willReturn(updatedOrderHistory);

        mockMvc.perform(put("/api/order-histories/1")
                .param("newOrderStatus", "SHIPPED")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(1))
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.orderStatus").value("SHIPPED"));
    }

    @Test
    @DisplayName("주문 내역 삭제")
    void deleteOrderHistory() throws Exception {
        doNothing().when(orderHistoryUseCase).deleteOrderHistory(anyLong());

        mockMvc.perform(delete("/api/order-histories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
