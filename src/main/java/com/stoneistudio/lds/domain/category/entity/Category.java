package com.stoneistudio.lds.domain.category.entity;

import com.stoneistudio.lds.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @JsonManagedReference
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Category> children = new ArrayList<>();

    @Column(name = "depth")
    private Integer depth;

    public Category(String name) {
        this.name = name;
        this.depth = 0;
    }

    public void addChild(Category child) {
        this.children.add(child);
        child.setParent(this);
        child.setDepth(this.depth + 1);
    }

    public void removeChild(Category child) {
        this.children.remove(child);
        child.setParent(null);
    }
}
