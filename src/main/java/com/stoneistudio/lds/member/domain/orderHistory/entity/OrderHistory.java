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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderHistoryId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private LocalDateTime orderDate;
}