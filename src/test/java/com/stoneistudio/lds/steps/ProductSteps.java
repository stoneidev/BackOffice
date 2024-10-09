package com.stoneistudio.lds.steps;

import com.stoneistudio.lds.application.port.in.ProductInputPort;
import com.stoneistudio.lds.domain.entity.Product;
import com.stoneistudio.lds.domain.value.ProductName;

import io.cucumber.java.en.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@CucumberContextConfiguration
@SpringBootTest(properties = { "spring.profiles.active=test" })
public class ProductSteps {

    @Autowired
    private ProductInputPort productInputPort;

    private Product product;
    private List<Product> productList;
    private Long productId;
    private Exception lastException;

    @Given("a product with name {string}")
    public void a_product_with_name(String name) {
        product = new Product(name);
    }

    @When("I add the product")
    public void i_add_the_product() {
        productInputPort.addProduct(product);
    }

    @Then("the product should be saved successfully")
    public void the_product_should_be_saved_successfully() {
        assertNotNull(product.getProductId());
    }

    @Given("there are existing products in the system")
    public void there_are_existing_products_in_the_system() {
        productInputPort.addProduct(new Product("Existing Product 1"));
        productInputPort.addProduct(new Product("Existing Product 2"));
    }

    @When("I request all products")
    public void i_request_all_products() {
        productList = productInputPort.getAllProducts();
    }

    @Then("I should receive a list of all products")
    public void i_should_receive_a_list_of_all_products() {
        assertFalse(productList.isEmpty());
        assertTrue(productList.size() >= 2);
    }

    @Given("a product with ID {long} exists in the system")
    public void a_product_with_id_exists_in_the_system(Long id) {
        product = new Product("Test Product");
        productInputPort.addProduct(product);
        productId = product.getProductId();
    }

    @When("I request the product with ID {long}")
    public void i_request_the_product_with_id(Long id) {
        product = productInputPort.getProductById(id);
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
        product.setProductName(new ProductName(newName));
        productInputPort.updateProduct(product);
    }

    @Then("the product should be updated successfully")
    public void the_product_should_be_updated_successfully() {
        Product updatedProduct = productInputPort.getProductById(productId);
        assertEquals("Updated Product", updatedProduct.getProductName().getName());
    }

    @Given("a product with ID {long} does not exist in the system")
    public void a_product_with_id_does_not_exist_in_the_system(Long id) {
        productId = id;
        product = new Product("Non-existent Product");
        product.setProductId(productId);
    }

    @When("I try to update the non-existent product")
    public void i_try_to_update_the_non_existent_product() {
        productInputPort.updateProduct(product);
    }

    @Then("no update should occur")
    public void no_update_should_occur() {
        assertNull(productInputPort.getProductById(productId));
    }

    @When("I delete the product with ID {long}")
    public void i_delete_the_product_with_id(Long id) {
        productInputPort.deleteProduct(id);
    }

    @Then("the product should be removed from the system")
    public void the_product_should_be_removed_from_the_system() {
        assertNull(productInputPort.getProductById(productId));
    }

    @When("I try to delete the non-existent product")
    public void i_try_to_delete_the_non_existent_product() {
        productInputPort.deleteProduct(productId);
    }

    @Then("no deletion should occur")
    public void no_deletion_should_occur() {
        // 존재하지 않는 제품을 삭제하려고 시도해도 예외가 발생하지 않아야 합니다.
        assertDoesNotThrow(() -> productInputPort.deleteProduct(productId));
    }

    @Given("a product with id {long} and name {string}")
    public void a_product_with_id_and_name(Long id, String name) {
        product = new Product(name);
        product.setProductId(id);
    }

    @When("the product is added")
    public void the_product_is_added() {
        productInputPort.addProduct(product);
    }

    @Then("the product should be retrievable by id {long}")
    public void the_product_should_be_retrievable_by_id(Long id) {
        Product retrievedProduct = productInputPort.getProductById(id);
        assertNotNull(retrievedProduct);
        assertEquals(product.getProductName(), retrievedProduct.getProductName());
    }

    @Then("the product with ID {long} should not exist in the system")
    public void the_product_with_id_should_not_exist_in_the_system(Long id) {
        // 제품이 존재하지 않을 때 IllegalArgumentException이 발생해야 합니다.
        assertThrows(IllegalArgumentException.class, () -> productInputPort.getProductById(id));
    }
}
