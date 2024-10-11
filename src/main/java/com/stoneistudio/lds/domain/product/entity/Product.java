package com.stoneistudio.lds.domain.product.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@Getter
@NoArgsConstructor
public class Product {
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

    public void setName(String name) {
        this.name = name;
    }
}
