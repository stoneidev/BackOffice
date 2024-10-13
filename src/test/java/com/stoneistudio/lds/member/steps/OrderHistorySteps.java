package com.stoneistudio.lds.member.steps;

import com.stoneistudio.lds.member.application.usecase.OrderHistoryUseCase;
import com.stoneistudio.lds.member.domain.orderHistory.entity.OrderHistory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderHistorySteps {

    private static final Logger logger = LoggerFactory.getLogger(OrderHistorySteps.class);

    @Autowired
    private OrderHistoryUseCase orderHistoryUseCase;

    private OrderHistory createdOrderHistory;
    private OrderHistory retrievedOrderHistory;
    private List<OrderHistory> retrievedOrderHistories;
    private Long testOrderHistoryId;

    @Given("회원 ID가 {long}이고 주문 ID가 {long}인 주문이 존재한다")
    public void 주문이_존재한다(Long memberId, Long orderId) {
        testOrderHistoryId = orderId;
        logger.info("주문이 존재한다: memberId={}, orderId={}", memberId, orderId);
    }

    @When("{string} 상태로 새로운 주문 내역을 생성한다")
    public void 새로운_주문_내역을_생성한다(String orderStatus) {
        logger.info("새로운 주문 내역 생성 시도: memberId=1, orderId={}, status={}", testOrderHistoryId, orderStatus);
        createdOrderHistory = orderHistoryUseCase.createOrderHistory(1L, testOrderHistoryId, orderStatus);
        logger.info("생성된 주문 내역: {}", createdOrderHistory);
    }

    @Then("주문 내역이 성공적으로 생성되어야 한다")
    public void 주문_내역이_성공적으로_생성되어야_한다() {
        assertNotNull(createdOrderHistory, "생성된 주문 내역이 null입니다.");
        assertNotNull(createdOrderHistory.getOrderHistoryId(), "생성된 주문 내역의 ID가 null입니다.");
        logger.info("주문 내역 생성 확인: {}", createdOrderHistory);
    }

    @Then("생성된 주문 내역의 상태는 {string}이어야 한다")
    public void 생성된_주문_내역의_상태_확인(String expectedStatus) {
        assertNotNull(createdOrderHistory, "주문 내역이 생성되지 않았습니다.");
        assertEquals(expectedStatus, createdOrderHistory.getOrderStatus());
        logger.info("주문 내역 상태 확인: expected={}, actual={}", expectedStatus, createdOrderHistory.getOrderStatus());
    }

    @Given("주문 내역 ID가 {long}인 주문 내역이 존재한다")
    public void 주문_내역이_존재한다(Long orderHistoryId) {
        testOrderHistoryId = orderHistoryId;
        orderHistoryUseCase.createOrderHistory(1L, 100L, "PENDING");
    }

    @When("주문 내역 ID {long}로 주문 내역을 조회한다")
    public void 주문_내역을_조회한다(Long orderHistoryId) {
        retrievedOrderHistory = orderHistoryUseCase.getOrderHistoryById(orderHistoryId);
    }

    @Then("해당 주문 내역이 정상적으로 반환되어야 한다")
    public void 주문_내역이_정상적으로_반환되어야_한다() {
        assertNotNull(retrievedOrderHistory);
        assertEquals(testOrderHistoryId, retrievedOrderHistory.getOrderHistoryId());
    }

    @Given("회원 ID가 {long}인 회원의 주문 내역이 {int}개 존재한다")
    public void 회원의_주문_내역이_존재한다(Long memberId, int count) {
        for (int i = 0; i < count; i++) {
            orderHistoryUseCase.createOrderHistory(memberId, 100L + i, "PENDING");
        }
    }

    @When("회원 ID {long}로 주문 내역을 조회한다")
    public void 회원의_주문_내역을_조회한다(Long memberId) {
        retrievedOrderHistories = orderHistoryUseCase.getOrderHistoriesByMemberId(memberId);
    }

    @Then("해당 회원의 모든 주문 내역이 반환되어야 한다")
    public void 회원의_모든_주문_내역이_반환되어야_한다() {
        assertNotNull(retrievedOrderHistories);
    }

    @Then("반환된 주문 내역의 개수는 {int}개여야 한다")
    public void 반환된_주문_내역의_개수_확인(int expectedCount) {
        assertEquals(expectedCount, retrievedOrderHistories.size());
    }

    @Given("주문 내역 ID가 {long}이고 상태가 {string}인 주문 내역이 존재한다")
    public void 특정_상태의_주문_내역이_존재한다(Long orderHistoryId, String initialStatus) {
        testOrderHistoryId = orderHistoryId;
        orderHistoryUseCase.createOrderHistory(1L, 100L, initialStatus);
    }

    @When("주문 내역의 상태를 {string}로 업데이트한다")
    public void 주문_내역의_상태를_업데이트한다(String newStatus) {
        orderHistoryUseCase.updateOrderHistory(testOrderHistoryId, newStatus);
    }

    @Then("주문 내역의 상태가 성공적으로 {string}로 변경되어야 한다")
    public void 주문_내역의_상태_변경_확인(String expectedStatus) {
        OrderHistory updatedOrderHistory = orderHistoryUseCase.getOrderHistoryById(testOrderHistoryId);
        assertEquals(expectedStatus, updatedOrderHistory.getOrderStatus());
    }

    @When("주문 내역 ID {long}로 주문 내역을 삭제한다")
    public void 주문_내역을_삭제한다(Long orderHistoryId) {
        orderHistoryUseCase.deleteOrderHistory(orderHistoryId);
    }

    @Then("해당 주문 내역이 성공적으로 삭제되어야 한다")
    public void 주문_내역이_성공적으로_삭제되어야_한다() {
        OrderHistory deletedOrderHistory = orderHistoryUseCase.getOrderHistoryById(testOrderHistoryId);
        assertNull(deletedOrderHistory);
    }

    @Then("삭제된 주문 내역을 조회하면 찾을 수 없어야 한다")
    public void 삭제된_주문_내역_조회_불가() {
        assertNull(orderHistoryUseCase.getOrderHistoryById(testOrderHistoryId));
    }
}
