package com.stoneistudio.lds.domain.product.entity;

import com.stoneistudio.lds.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category_id")
    private Long categoryId;

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, Long categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public void removeCategoryId() {
        this.categoryId = null;
    }
}
