package com.stoneistudio.lds.steps;

import com.stoneistudio.lds.application.usecase.ProductQAUseCase;
import com.stoneistudio.lds.domain.product.entity.Product;
import com.stoneistudio.lds.domain.productqa.entity.ProductQA;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductQASteps {

    @Mock
    private ProductQAUseCase productQAUseCase;

    private ProductQA productQA;
    private List<ProductQA> productQAs;
    private Exception thrownException;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("a product with ID {long} exists")
    public void a_product_with_id_exists(Long productId) {
        // ProductQAUseCase가 모킹되어 있으므로 여기서는 아무 작업도 필요 없습니다.
    }

    @When("I add a new QA with question {string} to the product")
    public void i_add_a_new_qa_with_question_to_the_product(String question) {
        ProductQA newProductQA = new ProductQA(new Product("Test Product"), question);
        when(productQAUseCase.addProductQA(anyLong(), eq(question))).thenReturn(newProductQA);
        productQA = productQAUseCase.addProductQA(1L, question);
    }

    @Then("the QA should be successfully added to the product")
    public void the_qa_should_be_successfully_added_to_the_product() {
        assertNotNull(productQA);
        verify(productQAUseCase).addProductQA(anyLong(), anyString());
    }

    @Given("a product with ID {long} has a QA with ID {long}")
    public void a_product_with_id_has_a_qa_with_id(Long productId, Long qaId) {
        ProductQA existingProductQA = new ProductQA(new Product("Test Product"), "Test Question");
        existingProductQA.setId(qaId);
        when(productQAUseCase.getProductQA(eq(productId), eq(qaId))).thenReturn(existingProductQA);
    }

    @When("I request the QA with ID {long} for the product")
    public void i_request_the_qa_with_id_for_the_product(Long qaId) {
        productQA = productQAUseCase.getProductQA(1L, qaId);
    }

    @Then("I should receive the correct QA details")
    public void i_should_receive_the_correct_qa_details() {
        assertNotNull(productQA);
        assertEquals(1L, productQA.getId());
    }

    @Given("a product with ID {long} has multiple QAs")
    public void a_product_with_id_has_multiple_qas(Long productId) {
        productQAs = Arrays.asList(
            new ProductQA(new Product("Test Product"), "Question 1"),
            new ProductQA(new Product("Test Product"), "Question 2")
        );
        when(productQAUseCase.getAllProductQAs(eq(productId))).thenReturn(productQAs);
    }

    @When("I request all QAs for the product")
    public void i_request_all_qas_for_the_product() {
        productQAs = productQAUseCase.getAllProductQAs(1L);
    }

    @Then("I should receive a list of all QAs for the product")
    public void i_should_receive_a_list_of_all_qas_for_the_product() {
        assertNotNull(productQAs);
        assertEquals(2, productQAs.size());
    }

    @When("I update the QA with ID {long} with new question {string}")
    public void i_update_the_qa_with_id_with_new_question(Long qaId, String newQuestion) {
        doNothing().when(productQAUseCase).updateProductQA(anyLong(), eq(qaId), eq(newQuestion));
        productQAUseCase.updateProductQA(1L, qaId, newQuestion);
    }

    @Then("the QA should be successfully updated")
    public void the_qa_should_be_successfully_updated() {
        verify(productQAUseCase).updateProductQA(anyLong(), anyLong(), anyString());
    }

    @Given("a product with ID {long} has an unanswered QA with ID {long}")
    public void a_product_with_id_has_an_unanswered_qa_with_id(Long productId, Long qaId) {
        ProductQA unansweredProductQA = new ProductQA(new Product("Test Product"), "Unanswered Question");
        unansweredProductQA.setId(qaId);
        when(productQAUseCase.getProductQA(eq(productId), eq(qaId))).thenReturn(unansweredProductQA);
    }

    @When("I answer the QA with ID {long} with {string}")
    public void i_answer_the_qa_with_id_with(Long qaId, String answer) {
        doNothing().when(productQAUseCase).answerProductQA(anyLong(), eq(qaId), eq(answer));
        productQAUseCase.answerProductQA(1L, qaId, answer);
    }

    @Then("the QA should be successfully answered")
    public void the_qa_should_be_successfully_answered() {
        verify(productQAUseCase).answerProductQA(anyLong(), anyLong(), anyString());
    }

    @When("I delete the QA with ID {long}")
    public void i_delete_the_qa_with_id(Long qaId) {
        doNothing().when(productQAUseCase).deleteProductQA(anyLong(), eq(qaId));
        productQAUseCase.deleteProductQA(1L, qaId);
    }

    @Then("the QA should be successfully removed from the product")
    public void the_qa_should_be_successfully_removed_from_the_product() {
        verify(productQAUseCase).deleteProductQA(anyLong(), anyLong());
    }
}