package com.stoneistudio.lds.steps;

import com.stoneistudio.lds.application.port.in.ProductInputPort;
import com.stoneistudio.lds.domain.product.entity.Product;
import com.stoneistudio.lds.domain.product.value.ProductName;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(properties = { "spring.profiles.active=test" })
public class ProductSteps {

    @Autowired
    private ProductInputPort productInputPort;

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
        try {
            product = productInputPort.getProductById(productId);
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
        assertNotNull(lastException);
        assertTrue(lastException instanceof IllegalArgumentException);
    }

    @When("I update the product name to {string}")
    public void i_update_the_product_name_to(String newName) {
        product.setProductName(new ProductName(newName));
        try {
            productInputPort.updateProduct(product);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
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
        try {
            productInputPort.updateProduct(product);
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
            productInputPort.deleteProduct(productId);
        } catch (IllegalArgumentException e) {
            lastException = e;
        }
    }

    @Then("it should be deleted successfully")
    public void itShouldBeDeletedSuccessfully() {
        assertThrows(IllegalArgumentException.class, () -> productInputPort.getProductById(productId));
    }

    @Then("the product should be removed from the system")
    public void the_product_should_be_removed_from_the_system() {
        assertThrows(IllegalArgumentException.class, () -> productInputPort.getProductById(productId));
    }

    @When("I try to delete the non-existent product")
    public void i_try_to_delete_the_non_existent_product() {
        try {
            productInputPort.deleteProduct(999999L);
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
        assertThrows(IllegalArgumentException.class, () -> productInputPort.getProductById(id));
    }
}
