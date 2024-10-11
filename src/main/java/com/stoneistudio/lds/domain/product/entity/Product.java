package com.stoneistudio.lds.domain.product.entity;

import com.stoneistudio.lds.domain.category.entity.Category;
import com.stoneistudio.lds.domain.product.value.ProductName;
import com.stoneistudio.lds.domain.productqa.entity.ProductQA;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductQA> productQAs = new ArrayList<>();

    public Product(String productName) {
        this.productName = new ProductName(productName);
    }

    // ProductQA CRUD 메서드

    public ProductQA addProductQA(String question) {
        ProductQA productQA = new ProductQA(this, question);
        this.productQAs.add(productQA);
        return productQA;
    }

    public ProductQA getProductQA(Long qaId) {
        return this.productQAs.stream()
                .filter(qa -> qa.getId().equals(qaId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ProductQA not found"));
    }

    public List<ProductQA> getAllProductQAs() {
        return new ArrayList<>(this.productQAs);
    }

    public void updateProductQA(Long qaId, String newQuestion) {
        ProductQA productQA = getProductQA(qaId);
        productQA.setQuestion(newQuestion);
    }

    public void answerProductQA(Long qaId, String answer) {
        ProductQA productQA = getProductQA(qaId);
        productQA.setAnswer(answer);
    }

    public void deleteProductQA(Long qaId) {
        ProductQA productQA = getProductQA(qaId);
        this.productQAs.remove(productQA);
    }
}
