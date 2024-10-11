Feature: Product Management

  Scenario: Add a new product
    Given a product with name "New Product"
    When I add the product
    Then the product should be saved successfully

  Scenario: 카테고리별 제품 조회
    Given 카테고리 ID가 1인 카테고리가 존재합니다
    And 해당 카테고리에 "제품 1"과 "제품 2"가 속해 있습니다
    When 카테고리 ID 1로 제품을 조회합니다
    Then 조회된 제품 목록에는 "제품 1"과 "제품 2"가 포함되어 있어야 합니다