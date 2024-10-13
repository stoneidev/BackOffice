package com.stoneistudio.lds.member.framework.adapters.output;

import com.stoneistudio.lds.member.application.port.out.OrderHistoryOutputPort;
import com.stoneistudio.lds.member.domain.orderHistory.entity.OrderHistory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class JpaOrderHistoryAdapter implements OrderHistoryOutputPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderHistory save(OrderHistory orderHistory) {
        if (orderHistory.getOrderHistoryId() == null) {
            entityManager.persist(orderHistory);
        } else {
            orderHistory = entityManager.merge(orderHistory);
        }
        return orderHistory;
    }

    @Override
    public OrderHistory findById(Long orderHistoryId) {
        return entityManager.find(OrderHistory.class, orderHistoryId);
    }

    @Override
    public List<OrderHistory> findByMemberId(Long memberId) {
        return entityManager.createQuery(
                "SELECT oh FROM OrderHistory oh WHERE oh.memberId = :memberId", OrderHistory.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public void delete(OrderHistory orderHistory) {
        entityManager.remove(entityManager.contains(orderHistory) ? orderHistory : entityManager.merge(orderHistory));
    }
}
