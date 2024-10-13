package com.stoneistudio.lds.member.domain.orderHistory.entity;

import com.stoneistudio.lds.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
@Getter
@Setter
@NoArgsConstructor
public class OrderHistory extends BaseEntity {
    public OrderHistory(Long memberId, Long orderId, String orderStatus) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderHistoryId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String orderStatus;

    @Column(nullable = false)
    private LocalDateTime orderDate;
}
