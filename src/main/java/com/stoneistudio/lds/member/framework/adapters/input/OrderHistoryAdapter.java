package com.stoneistudio.lds.member.framework.adapters.input;

import com.stoneistudio.lds.member.application.usecase.OrderHistoryUseCase;
import com.stoneistudio.lds.member.domain.orderHistory.entity.OrderHistory;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-histories")
public class OrderHistoryAdapter {

    private final OrderHistoryUseCase orderHistoryUseCase;

    @Autowired
    public OrderHistoryAdapter(OrderHistoryUseCase orderHistoryUseCase) {
        this.orderHistoryUseCase = orderHistoryUseCase;
    }

    @Operation(summary = "주문 내역 생성", description = "새로운 주문 내역을 생성합니다.")
    @PostMapping
    public ResponseEntity<OrderHistory> createOrderHistory(@RequestParam Long memberId, @RequestParam Long orderId,
            @RequestParam String orderStatus) {
        OrderHistory orderHistory = orderHistoryUseCase.createOrderHistory(memberId, orderId, orderStatus);
        return ResponseEntity.ok(orderHistory);
    }

    @Operation(summary = "주문 내역 조회", description = "지정된 ID의 주문 내역을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<OrderHistory> getOrderHistoryById(@PathVariable Long id) {
        OrderHistory orderHistory = orderHistoryUseCase.getOrderHistoryById(id);
        return ResponseEntity.ok(orderHistory);
    }

    @Operation(summary = "회원별 주문 내역 조회", description = "지정된 회원의 모든 주문 내역을 조회합니다.")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<OrderHistory>> getOrderHistoriesByMemberId(@PathVariable Long memberId) {
        List<OrderHistory> orderHistories = orderHistoryUseCase.getOrderHistoriesByMemberId(memberId);
        return ResponseEntity.ok(orderHistories);
    }

    @Operation(summary = "주문 내역 수정", description = "지정된 ID의 주문 내역 상태를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<OrderHistory> updateOrderHistory(@PathVariable Long id, @RequestParam String newOrderStatus) {
        OrderHistory updatedOrderHistory = orderHistoryUseCase.updateOrderHistory(id, newOrderStatus);
        return ResponseEntity.ok(updatedOrderHistory);
    }

    @Operation(summary = "주문 내역 삭제", description = "지정된 ID의 주문 내역을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderHistory(@PathVariable Long id) {
        orderHistoryUseCase.deleteOrderHistory(id);
        return ResponseEntity.ok().build();
    }
}
