package com.stoneistudio.lds.domain.product.entity;

import com.stoneistudio.lds.domain.category.entity.Category;
import com.stoneistudio.lds.domain.product.value.ProductName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Embedded
    private ProductName productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(String productName) {
        this.productName = new ProductName(productName);
    }
}
