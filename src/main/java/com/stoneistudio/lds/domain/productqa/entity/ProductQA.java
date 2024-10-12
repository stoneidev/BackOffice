package com.stoneistudio.lds.domain.productqa.entity;

import com.stoneistudio.lds.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_qa")
@Getter
@Setter
@NoArgsConstructor
public class ProductQA extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qaId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String question;

    @Column
    private String answer;

    public ProductQA(Long productId, String question) {
        this.productId = productId;
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
