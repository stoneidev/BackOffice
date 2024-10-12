package com.stoneistudio.lds.steps;

import com.stoneistudio.lds.application.dto.ProductDetailDTO;
import com.stoneistudio.lds.application.usecase.ProductCommentUseCase;
import com.stoneistudio.lds.application.usecase.ProductQAUseCase;
import com.stoneistudio.lds.application.usecase.ProductQueryUseCase;
import com.stoneistudio.lds.application.usecase.ProductUseCase;
import com.stoneistudio.lds.domain.product.entity.Product;
import com.stoneistudio.lds.domain.productqa.entity.ProductQA;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class ProductQuerySteps {

    @Autowired
    private ProductQueryUseCase productQueryUseCase;

    @Autowired
    private ProductUseCase productUseCase;

    @Autowired
    private ProductQAUseCase productQAUseCase;

    @Autowired
    private ProductCommentUseCase productCommentUseCase;

    private Product product;
    private ProductDetailDTO productDetail;
    private Long productId;

    @Given("제품 ID가 {long}인 제품이 존재합니다")
    public void 제품이_존재합니다(Long id) {
        this.productId = id;
        this.product = new Product("제품 이름 " + id, 1L);
        productUseCase.addProduct(this.product);
        this.productId = this.product.getProductId(); // 실제 저장된 ID를 가져옵니다
        productUseCase.addProduct(this.product);
    }

    @Given("해당 제품에 대한 댓글과 Q&A가 있습니다")
    public void 제품에_댓글과_QA가_있습니다() {
        productCommentUseCase.addProductComment(productId, "댓글 1", "jngkim");
        productCommentUseCase.addProductComment(productId, "댓글 2", "jngkim");

        ProductQA qa1 = productQAUseCase.addProductQA(productId, "질문 1");
        productQAUseCase.answerProductQA(productId, qa1.getId(), "답변 1");

        ProductQA qa2 = productQAUseCase.addProductQA(productId, "질문 2");
        productQAUseCase.answerProductQA(productId, qa2.getId(), "답변 2");
    }

    @When("제품 ID {long}로 상세 정보를 조회합니다")
    public void 제품_상세_정보를_조회합니다(Long id) {
        productDetail = productQueryUseCase.getProductDetails(this.productId);
    }

    @Then("제품의 이름, 카테고리, 댓글, Q&A 정보를 모두 받아야 합니다")
    public void 제품_상세_정보를_확인합니다() {
        assertNotNull(productDetail);
        assertEquals(this.productId, productDetail.getProductId());
        assertNotNull(productDetail.getProductName());
        assertNotNull(productDetail.getCategoryName());
        assertFalse(productDetail.getComments().isEmpty());
        assertFalse(productDetail.getQaItems().isEmpty());
    }

    @Given("제품 ID가 {long}인 제품이 존재하지 않습니다")
    public void 제품이_존재하지_않습니다(Long id) {
        this.productId = id;
    }

    @Then("제품 정보를 찾을 수 없다는 응답을 받아야 합니다")
    public void 제품_정보를_찾을_수_없음을_확인합니다() {
        assertNull(productDetail);
    }
}
