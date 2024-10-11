package com.stoneistudio.lds.domain.productqa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_qa")
@Getter
@Setter
@NoArgsConstructor
public class ProductQA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String question;

    @Column
    private String answer;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime answeredAt;

    public ProductQA(Long productId, String question) {
        this.productId = productId;
        this.question = question;
        this.createdAt = LocalDateTime.now();
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        this.answeredAt = LocalDateTime.now();
    }
}