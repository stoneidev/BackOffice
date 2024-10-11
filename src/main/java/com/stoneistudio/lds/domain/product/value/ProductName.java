package com.stoneistudio.lds.domain.product.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class ProductName {

    @Column(name = "product_name")
    private String name; // 필드 이름 변경

    public ProductName(String name) { // 생성자 변경
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
