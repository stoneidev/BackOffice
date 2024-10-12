package com.stoneistudio.lds.product.domain.product.entity;

import com.stoneistudio.lds.base.BaseEntity;
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

    @Column(name = "price")
    private Long price;

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

    public void changeCategoryId(Long newCategoryId) {
        this.categoryId = newCategoryId;
    }

    public void changeProductPrice(Long newPrice) {
        this.price = newPrice;
    }
}
