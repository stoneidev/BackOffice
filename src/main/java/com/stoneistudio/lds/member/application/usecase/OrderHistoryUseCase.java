package com.stoneistudio.lds.member.application.usecase;

import com.stoneistudio.lds.member.domain.orderHistory.entity.OrderHistory;
import java.util.List;

public interface OrderHistoryUseCase {
    OrderHistory createOrderHistory(Long memberId, Long orderId, String orderStatus);

    OrderHistory getOrderHistoryById(Long orderHistoryId);

    List<OrderHistory> getOrderHistoriesByMemberId(Long memberId);

    OrderHistory updateOrderHistory(Long orderHistoryId, String newOrderStatus);

    void deleteOrderHistory(Long orderHistoryId);
}
