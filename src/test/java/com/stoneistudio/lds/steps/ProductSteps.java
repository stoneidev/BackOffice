package com.stoneistudio.lds.steps;

import com.stoneistudio.lds.application.usecase.ProductUseCase;
import com.stoneistudio.lds.domain.product.entity.Product;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(properties = { "spring.profiles.active=test" })
public class ProductSteps {

    @Autowired
    private ProductUseCase productUseCase;

    private Product product;
    private List<Product> productList;
    private Long productId;
    private Exception lastException;

    @After
    public void tearDown() {
        product = null;
        productList = null;
        productId = null;
        lastException = null;
    }

    @Given("a product with name {string}")
    public void a_product_with_name(String name) {
        product = new Product(name);
    }

    @When("I add the product")
    public void i_add_the_product() {
        product.setCreatedAt(LocalDateTime.now()); // 이 줄을 추가
        productUseCase.addProduct(product);
    }

    @Then("the product should be saved successfully")
    public void the_product_should_be_saved_successfully() {
        assertNotNull(product.getProductId());
    }

    @Given("there are existing products in the system")
    public void there_are_existing_products_in_the_system() {
        productUseCase.addProduct(new Product("Existing Product 1"));
        productUseCase.addProduct(new Product("Existing Product 2"));
    }

    @When("I request all products")
    public void i_request_all_products() {
        productList = productUseCase.getAllProducts();
    }

    @Then("I should receive a list of all products")
    public void i_should_receive_a_list_of_all_products() {
        assertFalse(productList.isEmpty());
        assertTrue(productList.size() >= 2);
    }

    @Given("a product with ID {long} exists in the system")
    public void a_product_with_id_exists_in_the_system(Long id) {
        product = new Product("Test Product");
        productUseCase.addProduct(product);
        productId = product.getProductId();
    }

    @When("I request the product with ID {long}")
    public void i_request_the_product_with_id(Long id) {
        try {
            product = productUseCase.getProductById(productId);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @When("I request the product with ID not exist")
    public void i_request_the_product_with_id_not_exist() {
        try {
            product = productUseCase.getProductById(999L);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @Then("I should receive the correct product details")
    public void i_should_receive_the_correct_product_details() {
        assertNotNull(product);
        assertEquals(productId, product.getProductId());
    }

    @Then("I should receive no product")
    public void i_should_receive_no_product() {
        assertNull(product);
    }

    @When("I update the product name to {string}")
    public void i_update_the_product_name_to(String newName) {
        product.setName(newName);
        try {
            productUseCase.updateProduct(product);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @Then("the product should be updated successfully")
    public void the_product_should_be_updated_successfully() {
        Product updatedProduct = productUseCase.getProductById(productId);
        assertEquals("Updated Product", updatedProduct.getName());
    }

    @Given("a product with ID {long} does not exist in the system")
    public void a_product_with_id_does_not_exist_in_the_system(Long id) {
        productId = id;
        product = new Product("Non-existent Product");
        product.setProductId(productId);
    }

    @When("I try to update the non-existent product")
    public void i_try_to_update_the_non_existent_product() {
        try {
            productUseCase.updateProduct(product);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @Then("no update should occur")
    public void no_update_should_occur() {
        assertNotNull(lastException);
        assertTrue(lastException instanceof IllegalArgumentException);
    }

    @When("I delete the product with ID {long}")
    public void i_delete_the_product_with_id(Long id) {
        try {
            productUseCase.deleteProduct(productId);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @Then("it should be deleted successfully")
    public void itShouldBeDeletedSuccessfully() {
        assertThrows(IllegalArgumentException.class, () -> productUseCase.getProductById(productId));
    }

    @Then("the product should be removed from the system")
    public void the_product_should_be_removed_from_the_system() {
        assertNull(productUseCase.getProductById(productId));
    }

    @When("I try to delete the non-existent product")
    public void i_try_to_delete_the_non_existent_product() {
        try {
            productUseCase.deleteProduct(999999L);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @Then("no deletion should occur")
    public void no_deletion_should_occur() {
        assertNotNull(lastException);
        assertTrue(lastException instanceof IllegalArgumentException);
    }

    @Then("the product with ID {long} should not exist in the system")
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
}
