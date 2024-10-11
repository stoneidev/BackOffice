package com.stoneistudio.lds.domain.productqa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.stoneistudio.lds.domain.product.entity.Product;

@Entity
@Table(name = "product_qa")
@Getter
@Setter
@NoArgsConstructor
public class ProductQA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private String question;

    @Column
    private String answer;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime answeredAt;

    public ProductQA(Product product, String question) {
        this.product = product;
        this.question = question;
        this.createdAt = LocalDateTime.now();
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        this.answeredAt = LocalDateTime.now();
    }
}