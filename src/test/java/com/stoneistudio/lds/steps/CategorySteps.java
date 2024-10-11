package com.stoneistudio.lds.steps;

import com.stoneistudio.lds.application.usecase.CategoryUseCase;
import com.stoneistudio.lds.application.usecase.ProductUseCase;
import com.stoneistudio.lds.domain.category.entity.Category;
import com.stoneistudio.lds.domain.product.entity.Product;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class CategorySteps {

    @Autowired
    private CategoryUseCase categoryUseCase;

    @Autowired
    private ProductUseCase productUseCase;

    private String categoryName;
    private Category createdCategory;
    private Category parentCategory;
    private Product product;

    @Given("카테고리 이름 {string}이 주어졌을 때")
    public void 카테고리_이름이_주어졌을_때(String name) {
        this.categoryName = name;
    }

    @When("새로운 카테고리를 생성하면")
    public void 새로운_카테고리를_생성하면() {
        createdCategory = categoryUseCase.createCategory(categoryName, null);
    }

    @Then("카테고리가 성공적으로 저장되어야 한다")
    public void 카테고리가_성공적으로_저장되어야_한다() {
        assertNotNull(createdCategory);
        assertEquals(categoryName, createdCategory.getName());
    }

    @Given("{string} 카테고리가 존재할 때")
    public void 카테고리가_존재할_때(String name) {
        parentCategory = categoryUseCase.createCategory(name, null);
    }

    @When("{string}이라는 하위 카테고리를 생성하면")
    public void 이라는_하위_카테고리를_생성하면(String name) {
        createdCategory = categoryUseCase.createCategory(name, parentCategory.getCategoryId());
    }

    @Then("{string} 카테고리의 하위 카테고리로 {string}이 추가되어야 한다")
    public void 카테고리의_하위_카테고리로_이_추가되어야_한다(String parentName, String childName) {
        var updatedParent = categoryUseCase.getCategoryById(parentCategory.getCategoryId());
        assertTrue(updatedParent.getChildren().stream().anyMatch(c -> c.getName().equals(childName)));
    }

    @When("{string} 카테고리를 삭제하면")
    public void 카테고리를_삭제하면(String name) {
        var category = categoryUseCase.getCategoryById(parentCategory.getCategoryId());
        categoryUseCase.deleteCategory(category.getCategoryId());
    }

    @Then("{string} 카테고리가 시스템에서 제거되어야 한다")
    public void 카테고리가_시스템에서_제거되어야_한다(String name) {
        assertNull(categoryUseCase.getCategoryById(parentCategory.getCategoryId()));
    }

    @Given("{string} 카테고리와 {string} 제품이 존재할 때")
    public void 카테고리와_제품이_존재할_때(String categoryName, String productName) {
        parentCategory = categoryUseCase.createCategory(categoryName, null);
        product = new Product(productName);
        productUseCase.addProduct(product);
    }

    @When("{string} 제품을 {string} 카테고리에 추가하면")
    public void 제품을_카테고리에_추가하면(String productName, String categoryName) {
        categoryUseCase.addProductToCategory(product.getProductId(), parentCategory.getCategoryId());
    }

    @Then("{string} 제품은 {string} 카테고리에 속해야 한다")
    public void 제품은_카테고리에_속해야_한다(String productName, String categoryName) {
        var updatedProduct = productUseCase.getProductById(product.getProductId());
        assertNotNull(updatedProduct.getCategory());
        assertEquals(parentCategory.getCategoryId(), updatedProduct.getCategory().getCategoryId());
    }

    @Given("{string} 카테고리에 속한 {string} 제품이 있을 때")
    public void 카테고리에_속한_제품이_있을_때(String categoryName, String productName) {
        parentCategory = categoryUseCase.createCategory(categoryName, null);
        product = new Product(productName);
        productUseCase.addProduct(product);
        categoryUseCase.addProductToCategory(product.getProductId(), parentCategory.getCategoryId());
    }

    @When("{string} 제품을 카테고리에서 제거하면")
    public void 제품을_카테고리에서_제거하면(String productName) {
        categoryUseCase.removeProductFromCategory(product.getProductId());
    }

    @Then("{string} 제품은 어떤 카테고리에도 속하지 않아야 한다")
    public void 제품은_어떤_카테고리에도_속하지_않아야_한다(String productName) {
        var updatedProduct = (Product) productUseCase.findProductById(product.getProductId());
        assertNull(updatedProduct.getCategory());
    }
}