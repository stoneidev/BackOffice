package com.stoneistudio.lds.domain.productcomment.entity;

import com.stoneistudio.lds.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_comment")
@Getter
@Setter
@NoArgsConstructor
public class ProductComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    public ProductComment(Long productId, String content, String author) {
        this.productId = productId;
        this.content = content;
        this.author = author;
    }
}
