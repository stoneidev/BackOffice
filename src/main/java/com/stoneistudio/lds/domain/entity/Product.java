package com.stoneistudio.lds.domain.entity;

import com.stoneistudio.lds.domain.value.ProductName;
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
    private ProductName productName; // 변경된 부분

    public Product(String productName) {
        this.productName = new ProductName(productName);
    }
}
