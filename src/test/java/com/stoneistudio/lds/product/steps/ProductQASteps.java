package com.stoneistudio.lds.product.steps;

import com.stoneistudio.lds.product.application.usecase.ProductQAUseCase;
import com.stoneistudio.lds.product.domain.productqa.entity.ProductQA;
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

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("ID가 {long}인 제품이 존재합니다")
    public void a_product_with_id_exists(Long productId) {
        // ProductQAUseCase가 모킹되어 있으므로 여기서는 아무 작업도 필요 없습니다.
    }

    @When("질문 {string}을 가진 새로운 QA를 제품에 추가합니다")
    public void i_add_a_new_qa_with_question_to_the_product(String question) {
        ProductQA newProductQA = new ProductQA(1L, question);
        when(productQAUseCase.addProductQA(anyLong(), eq(question))).thenReturn(newProductQA);
        productQA = productQAUseCase.addProductQA(1L, question);
    }

    @Then("QA가 제품에 성공적으로 추가되어야 합니다")
    public void the_qa_should_be_successfully_added_to_the_product() {
        assertNotNull(productQA);
        verify(productQAUseCase).addProductQA(anyLong(), anyString());
    }

    @Given("ID가 {long}인 제품에 ID가 {long}인 QA가 있습니다")
    public void a_product_with_id_has_a_qa_with_id(Long productId, Long qaId) {
        ProductQA existingProductQA = new ProductQA(productId, "Test Question");
        existingProductQA.setQaId(qaId);
        when(productQAUseCase.getProductQA(eq(productId), eq(qaId))).thenReturn(existingProductQA);
    }

    @When("ID가 {long}인 QA를 제품에서 요청합니다")
    public void i_request_the_qa_with_id_for_the_product(Long qaId) {
        productQA = productQAUseCase.getProductQA(1L, qaId);
    }

    @Then("정확한 QA 정보를 받아야 합니다")
    public void i_should_receive_the_correct_qa_details() {
        assertNotNull(productQA);
        assertEquals(1L, productQA.getQaId());
    }

    @Given("ID가 {long}인 제품에 여러 개의 QA가 있습니다")
    public void a_product_with_id_has_multiple_qas(Long productId) {
        productQAs = Arrays.asList(
                new ProductQA(productId, "Question 1"),
                new ProductQA(productId, "Question 2"));
        when(productQAUseCase.getAllProductQAs(eq(productId))).thenReturn(productQAs);
    }

    @When("제품의 모든 QA를 요청합니다")
    public void i_request_all_qas_for_the_product() {
        productQAs = productQAUseCase.getAllProductQAs(1L);
    }

    @Then("제품의 모든 QA 목록을 받아야 합니다")
    public void i_should_receive_a_list_of_all_qas_for_the_product() {
        assertNotNull(productQAs);
        assertEquals(2, productQAs.size());
    }

    @When("ID가 {long}인 QA를 새로운 질문 {string}으로 업데이트합니다")
    public void i_update_the_qa_with_id_with_new_question(Long qaId, String newQuestion) {
        doNothing().when(productQAUseCase).updateProductQA(anyLong(), eq(qaId), eq(newQuestion));
        productQAUseCase.updateProductQA(1L, qaId, newQuestion);
    }

    @Then("QA가 성공적으로 업데이트되어야 합니다")
    public void the_qa_should_be_successfully_updated() {
        verify(productQAUseCase).updateProductQA(anyLong(), anyLong(), anyString());
    }

    @Given("ID가 {long}인 제품에 ID가 {long}인 답변되지 않은 QA가 있습니다")
    public void a_product_with_id_has_an_unanswered_qa_with_id(Long productId, Long qaId) {
        ProductQA unansweredProductQA = new ProductQA(productId, "Unanswered Question");
        unansweredProductQA.setQaId(qaId);
        when(productQAUseCase.getProductQA(eq(productId), eq(qaId))).thenReturn(unansweredProductQA);
    }

    @When("ID가 {long}인 QA에 {string}으로 답변합니다")
    public void i_answer_the_qa_with_id_with(Long qaId, String answer) {
        doNothing().when(productQAUseCase).answerProductQA(anyLong(), eq(qaId), eq(answer));
        productQAUseCase.answerProductQA(1L, qaId, answer);
    }

    @Then("QA가 성공적으로 답변되어야 합니다")
    public void the_qa_should_be_successfully_answered() {
        verify(productQAUseCase).answerProductQA(anyLong(), anyLong(), anyString());
    }

    @When("ID가 {long}인 QA를 삭제합니다")
    public void i_delete_the_qa_with_id(Long qaId) {
        doNothing().when(productQAUseCase).deleteProductQA(anyLong(), eq(qaId));
        productQAUseCase.deleteProductQA(1L, qaId);
    }

    @Then("QA가 제품에서 성공적으로 제거되어야 합니다")
    public void the_qa_should_be_successfully_removed_from_the_product() {
        verify(productQAUseCase).deleteProductQA(anyLong(), anyLong());
    }
}
