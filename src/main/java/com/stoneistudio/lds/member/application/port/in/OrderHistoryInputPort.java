package com.stoneistudio.lds.member.application.port.in;

import com.stoneistudio.lds.member.application.usecase.OrderHistoryUseCase;
import com.stoneistudio.lds.member.domain.orderHistory.entity.OrderHistory;
import com.stoneistudio.lds.member.application.port.out.OrderHistoryOutputPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderHistoryInputPort implements OrderHistoryUseCase {

    private final OrderHistoryOutputPort orderHistoryOutputPort;

    public OrderHistoryInputPort(OrderHistoryOutputPort orderHistoryOutputPort) {
        this.orderHistoryOutputPort = orderHistoryOutputPort;
    }

    @Override
    public OrderHistory createOrderHistory(Long memberId, Long orderId, String orderStatus) {
        OrderHistory orderHistory = new OrderHistory(memberId, orderId, orderStatus);
        return orderHistoryOutputPort.save(orderHistory);
    }

    @Override
    public OrderHistory getOrderHistoryById(Long orderHistoryId) {
        return orderHistoryOutputPort.findById(orderHistoryId);
    }

    @Override
    public List<OrderHistory> getOrderHistoriesByMemberId(Long memberId) {
        return orderHistoryOutputPort.findByMemberId(memberId);
    }

    @Override
    public OrderHistory updateOrderHistory(Long orderHistoryId, String newOrderStatus) {
        OrderHistory orderHistory = orderHistoryOutputPort.findById(orderHistoryId);
        if (orderHistory == null) {
            throw new IllegalArgumentException("주문 내역이 존재하지 않습니다.");
        }
        orderHistory.setOrderStatus(newOrderStatus);
        return orderHistoryOutputPort.save(orderHistory);
    }

    @Override
    public void deleteOrderHistory(Long orderHistoryId) {
        OrderHistory orderHistory = orderHistoryOutputPort.findById(orderHistoryId);
        if (orderHistory == null) {
            throw new IllegalArgumentException("주문 내역이 존재하지 않습니다.");
        }
        orderHistoryOutputPort.delete(orderHistory);
    }
}
