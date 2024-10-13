package com.stoneistudio.lds.member.application.port.out;

import com.stoneistudio.lds.member.domain.orderHistory.entity.OrderHistory;
import java.util.List;

public interface OrderHistoryOutputPort {
    OrderHistory save(OrderHistory orderHistory);
    OrderHistory findById(Long orderHistoryId);
    List<OrderHistory> findByMemberId(Long memberId);
    void delete(OrderHistory orderHistory);
}
