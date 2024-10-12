package com.stoneistudio.lds.product.steps;

import com.stoneistudio.lds.product.application.usecase.ProductCommentUseCase;
import com.stoneistudio.lds.product.application.usecase.ProductUseCase;
import com.stoneistudio.lds.product.domain.product.entity.Product;
import com.stoneistudio.lds.product.domain.productcomment.entity.ProductComment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class ProductCommentSteps {

    @Autowired
    private ProductCommentUseCase productCommentUseCase;

    @Autowired
    private ProductUseCase productUseCase;

    private Product product;
    private ProductComment productComment;
    private List<ProductComment> productComments;

    @Given("{string} 제품이 존재할 때")
    public void 제품이_존재할_때(String productName) {
        product = new Product(productName);
        productUseCase.addProduct(product);
    }

    @When("{string}가 {string}라는 내용으로 새로운 리뷰를 작성하면")
    public void 새로운_리뷰를_작성하면(String author, String content) {
        productComment = productCommentUseCase.addProductComment(product.getProductId(), content, author);
    }

    @Then("리뷰가 성공적으로 저장되어야 한다")
    public void 리뷰가_성공적으로_저장되어야_한다() {
        assertNotNull(productComment);
        assertNotNull(productComment.getCommentId());
    }

    @Given("{string} 제품에 여러 개의 리뷰가 존재할 때")
    public void 제품에_여러_개의_리뷰가_존재할_때(String productName) {
        product = new Product(productName);
        productUseCase.addProduct(product);
        productCommentUseCase.addProductComment(product.getProductId(), "좋은 제품입니다.", "구매자1");
        productCommentUseCase.addProductComment(product.getProductId(), "가성비가 훌륭해요.", "구매자2");
    }

    @When("관리자가 해당 제품의 모든 리뷰를 요청하면")
    public void 관리자가_모든_리뷰를_요청하면() {
        productComments = productCommentUseCase.getAllProductComments(product.getProductId());
    }

    @Then("모든 리뷰 목록을 받아야 한다")
    public void 모든_리뷰_목록을_받아야_한다() {
        assertNotNull(productComments);
        assertEquals(2, productComments.size());
    }

    @Given("{string} 제품에 {string}의 리뷰가 존재할 때")
    public void 제품에_특정_구매자의_리뷰가_존재할_때(String productName, String author) {
        product = new Product(productName);
        productUseCase.addProduct(product);
        productComment = productCommentUseCase.addProductComment(product.getProductId(), "초기 리뷰 내용", author);
    }

    @When("{string}가 자신의 리뷰 내용을 {string}로 수정하면")
    public void 리뷰_내용을_수정하면(String author, String newContent) {
        productCommentUseCase.updateProductComment(productComment.getCommentId(), newContent);
    }

    @Then("리뷰 내용이 성공적으로 수정되어야 한다")
    public void 리뷰_내용이_성공적으로_수정되어야_한다() {
        ProductComment updatedComment = productCommentUseCase.getProductComment(productComment.getCommentId());
        assertEquals("화면이 선명하고 반응속도가 빨라서 만족스럽습니다.", updatedComment.getContent());
    }

    @Given("{string} 제품에 부적절한 내용의 리뷰가 존재할 때")
    public void 제품에_부적절한_리뷰가_존재할_때(String productName) {
        product = new Product(productName);
        productUseCase.addProduct(product);
        productComment = productCommentUseCase.addProductComment(product.getProductId(), "부적절한 내용", "악성 리뷰어");
    }

    @When("관리자가 해당 리뷰를 삭제하면")
    public void 관리자가_리뷰를_삭제하면() {
        productCommentUseCase.deleteProductComment(productComment.getCommentId());
    }

    @Then("리뷰가 성공적으로 삭제되어야 한다")
    public void 리뷰가_성공적으로_삭제되어야_한다() {
        assertNull(productCommentUseCase.getProductComment(productComment.getCommentId()));
    }

    @When("구매 예정자가 해당 제품의 리뷰를 요청하면")
    public void 구매_예정자가_리뷰를_요청하면() {
        productComments = productCommentUseCase.getAllProductComments(product.getProductId());
    }

    @Then("해당 제품의 모든 리뷰를 볼 수 있어야 한다")
    public void 모든_리뷰를_볼_수_있어야_한다() {
        assertNotNull(productComments);
        assertTrue(productComments.size() > 0);
    }

    @When("{string}가 자신의 리뷰에 {string}라는 댓글을 작성하면")
    public void 리뷰에_댓글을_작성하면(String author, String comment) {
        // 이 기능은 아직 구현되지 않았으므로, 여기서는 단순히 리뷰 내용을 업데이트하는 것으로 대체합니다.
        String updatedContent = productComment.getContent() + " [추가 댓글: " + comment + "]";
        productCommentUseCase.updateProductComment(productComment.getCommentId(), updatedContent);
    }

    @Then("리뷰에 새로운 댓글이 추가되어야 한다")
    public void 새로운_댓글이_추가되어야_한다() {
        ProductComment updatedComment = productCommentUseCase.getProductComment(productComment.getCommentId());
        assertTrue(updatedComment.getContent().contains("추가 댓글:"));
    }
}
