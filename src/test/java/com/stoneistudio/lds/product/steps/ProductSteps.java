package com.stoneistudio.lds.product.steps;

import com.stoneistudio.lds.product.application.usecase.CategoryUseCase;
import com.stoneistudio.lds.product.domain.category.entity.Category;
import com.stoneistudio.lds.product.domain.product.entity.Product;

import com.stoneistudio.lds.product.application.usecase.ProductUseCase;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductSteps {

    @Autowired
    private ProductUseCase productUseCase;

    @Autowired
    private CategoryUseCase categoryUseCase;

    private Product product;
    private List<Product> productList;
    private Long productId;
    private Exception lastException;
    private Category category1;
    private Category category2;

    @After
    public void tearDown() {
        product = null;
        productList = null;
        productId = null;
        lastException = null;
        category1 = null;
        category2 = null;
    }

    @Given("이름이 {string}인 제품이 있습니다")
    public void a_product_with_name(String name) {
        product = new Product(name);
    }

    @When("제품을 추가합니다")
    public void i_add_the_product() {
        product.setCreatedAt(LocalDateTime.now());
        productUseCase.addProduct(product);
    }

    @Then("제품이 성공적으로 저장되어야 합니다")
    public void the_product_should_be_saved_successfully() {
        assertNotNull(product.getProductId());
    }

    @Given("시스템에 기존 제품들이 존재합니다")
    public void there_are_existing_products_in_the_system() {
        productUseCase.addProduct(new Product("Existing Product 1"));
        productUseCase.addProduct(new Product("Existing Product 2"));
    }

    @When("모든 제품을 요청합니다")
    public void i_request_all_products() {
        productList = productUseCase.getAllProducts();
    }

    @Then("모든 제품 목록을 받아야 합니다")
    public void i_should_receive_a_list_of_all_products() {
        assertFalse(productList.isEmpty());
        assertTrue(productList.size() >= 2);
    }

    @Given("ID가 {long}인 제품이 시스템에 존재합니다")
    public void a_product_with_id_exists_in_the_system(Long id) {
        product = new Product("Test Product");
        productUseCase.addProduct(product);
        productId = product.getProductId();
    }

    @When("ID가 {long}인 제품을 요청합니다")
    public void i_request_the_product_with_id(Long id) {
        try {
            product = productUseCase.getProductById(productId);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @When("존재하지 않는 ID로 제품을 요청합니다")
    public void i_request_the_product_with_id_not_exist() {
        try {
            product = productUseCase.getProductById(999L);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @Then("정확한 제품 정보를 받아야 합니다")
    public void i_should_receive_the_correct_product_details() {
        assertNotNull(product);
        assertEquals(productId, product.getProductId());
    }

    @Then("제품을 받을 수 없어야 합니다")
    public void i_should_receive_no_product() {
        assertNull(product);
    }

    @When("제품 이름을 {string}로 업데이트합니다")
    public void i_update_the_product_name_to(String newName) {
        product.setName(newName);
        try {
            productUseCase.updateProduct(product);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @Then("제품이 성공적으로 업데이트되어야 합니다")
    public void the_product_should_be_updated_successfully() {
        Product updatedProduct = productUseCase.getProductById(productId);
        assertEquals("Updated Product", updatedProduct.getName());
    }

    @Given("ID가 {long}인 제품이 시스템에 존재하지 않습니다")
    public void a_product_with_id_does_not_exist_in_the_system(Long id) {
        productId = id;
        product = new Product("Non-existent Product");
        product.setProductId(productId);
    }

    @When("존재하지 않는 제품을 업데이트하려고 시도합니다")
    public void i_try_to_update_the_non_existent_product() {
        try {
            productUseCase.updateProduct(product);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @Then("업데이트가 발생하지 않아야 합니다")
    public void no_update_should_occur() {
        assertNotNull(lastException);
        assertTrue(lastException instanceof IllegalArgumentException);
    }

    @When("ID가 {long}인 제품을 삭제합니다")
    public void i_delete_the_product_with_id(Long id) {
        try {
            productUseCase.deleteProduct(productId);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @Then("성공적으로 삭제되어야 합니다")
    public void itShouldBeDeletedSuccessfully() {
        assertThrows(IllegalArgumentException.class, () -> productUseCase.getProductById(productId));
    }

    @Then("제품이 시스템에서 제거되어야 합니다")
    public void the_product_should_be_removed_from_the_system() {
        assertNull(productUseCase.getProductById(productId));
    }

    @When("존재하지 않는 제품을 삭제하려고 시도합니다")
    public void i_try_to_delete_the_non_existent_product() {
        try {
            productUseCase.deleteProduct(999999L);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @Then("삭제가 발생하지 않아야 합니다")
    public void no_deletion_should_occur() {
        assertNotNull(lastException);
        assertTrue(lastException instanceof IllegalArgumentException);
    }

    @Then("ID가 {long}인 제품이 시스템에 존재하지 않아야 합니다")
    public void the_product_with_id_should_not_exist_in_the_system(Long id) {
        assertThrows(IllegalArgumentException.class, () -> productUseCase.getProductById(id));
    }

    @Given("카테고리 ID가 {long}인 카테고리가 존재합니다")
    public void 카테고리_id가_인_카테고리가_존재합니다(Long categoryId) {
        // 이 step에서는 특별한 작업이 필요 없습니다. 카테고리의 존재를 가정합니다.
    }

    @Given("해당 카테고리에 {string}과 {string}가 속해 있습니다")
    public void 해당_카테고리에_과_가_속해_있습니다(String product1, String product2) {
        productUseCase.addProduct(new Product(product1, 1L));
        productUseCase.addProduct(new Product(product2, 1L));
    }

    @When("카테고리 ID {long}로 제품을 조회합니다")
    public void 카테고리_id로_제품을_조회합니다(Long categoryId) {
        productList = productUseCase.getProductsByCategory(categoryId);
    }

    @Then("조회된 제품 목록에는 {string}과 {string}가 포함되어 있어야 합니다")
    public void 조회된_제품_목록에는_과_가_포함되어_있어야_합니다(String product1, String product2) {
        assertNotNull(productList);
        assertEquals(2, productList.size());
        assertTrue(productList.stream().anyMatch(p -> p.getName().equals(product1)));
        assertTrue(productList.stream().anyMatch(p -> p.getName().equals(product2)));
    }

    @Given("{string} 카테고리와 {string} 카테고리가 존재합니다")
    public void 카테고리들이_존재합니다(String category1Name, String category2Name) {
        category1 = categoryUseCase.createCategory(category1Name, null);
        category2 = categoryUseCase.createCategory(category2Name, null);
    }

    @Given("{string} 제품이 {string} 카테고리에 속해 있습니다")
    public void 제품이_카테고리에_속해_있습니다(String productName, String categoryName) {
        product = new Product(productName, category1.getCategoryId());
        productUseCase.addProduct(product);
    }

    @When("{string} 제품의 카테고리를 {string}로 변경합니다")
    public void 제품의_카테고리를_변경합니다(String productName, String newCategoryName) {
        productUseCase.changeProductCategory(product.getProductId(), category2.getCategoryId());
    }

    @Then("{string} 제품은 {string} 카테고리에 속해야 합니다")
    public void 제품은_카테고리에_속해야_합니다(String productName, String categoryName) {
        Product updatedProduct = productUseCase.getProductById(product.getProductId());
        assertEquals(category2.getCategoryId(), updatedProduct.getCategoryId());
    }
}
