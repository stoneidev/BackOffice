package com.stoneistudio.lds.framework.adapters.input.rest;

import com.stoneistudio.lds.application.usecase.CategoryUseCase;
import com.stoneistudio.lds.domain.category.entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryAdapter {

    private final CategoryUseCase categoryUseCase;

    @Autowired
    public CategoryAdapter(CategoryUseCase categoryUseCase) {
        this.categoryUseCase = categoryUseCase;
    }

    @Operation(summary = "카테고리 생성", description = "새로운 카��고리를 생성합니다.")
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestParam String name, @RequestParam(required = false) Long parentId) {
        Category category = categoryUseCase.createCategory(name, parentId);
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "카테고리 삭제", description = "지정된 ID의 카테고리를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryUseCase.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "카테고리 조회", description = "지정된 ID의 카테고리를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryUseCase.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "모든 카테고리 조회", description = "모든 카테고리를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryUseCase.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "제품을 카테고리에 추가", description = "지정된 제품을 카테고리에 추가합니다.")
    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Void> addProductToCategory(@PathVariable Long categoryId, @PathVariable Long productId) {
        categoryUseCase.addProductToCategory(productId, categoryId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "제품을 카테고리에서 제거", description = "제품을 카테고리에서 제거합니다.")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> removeProductFromCategory(@PathVariable Long productId) {
        categoryUseCase.removeProductFromCategory(productId);
        return ResponseEntity.ok().build();
    }
}
