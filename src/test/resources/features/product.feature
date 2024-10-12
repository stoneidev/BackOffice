Feature: 제품 Management 

  Scenario: 새 제품 추가
    Given 이름이 "New Product"인 제품이 있습니다
    When 제품을 추가합니다
    Then 제품이 성공적으로 저장되어야 합니다

  Scenario: 카테고리별 제품 조회
    Given 카테고리 ID가 1인 카테고리가 존재합니다
    And 해당 카테고리에 "제품 1"과 "제품 2"가 속해 있습니다
    When 카테고리 ID 1로 제품을 조회합니다
    Then 조회된 제품 목록에는 "제품 1"과 "제품 2"가 포함되어 있어야 합니다

  Scenario: 제품의 카테고리 변경
    Given "전자제품" 카테고리와 "의류" 카테고리가 존재합니다
    And "스마트폰" 제품이 "전자제품" 카테고리에 속해 있습니다
    When "스마트폰" 제품의 카테고리를 "의류"로 변경합니다
    Then "스마트폰" 제품은 "의류" 카테고리에 속해야 합니다

  Scenario: 새 제품 추가
    Given 이름이 "New Product"인 제품이 있습니다
    When 제품을 추가합니다
    Then 제품이 성공적으로 저장되어야 합니다

  Scenario: 모든 제품 조회
    Given 시스템에 기존 제품들이 존재합니다
    When 모든 제품을 요청합니다
    Then 모든 제품 목록을 받아야 합니다

  Scenario: ID로 제품 조회
    Given ID가 1인 제품이 시스템에 존재합니다
    When ID가 1인 제품을 요청합니다
    Then 정확한 제품 정보를 받아야 합니다

  Scenario: 존재하지 않는 ID로 제품 조회
    Given ID가 1인 제품이 시스템에 존재합니다
    When 존재하지 않는 ID로 제품을 요청합니다
    Then 제품을 받을 수 없어야 합니다

  Scenario: 기존 제품 업데이트
    Given ID가 1인 제품이 시스템에 존재합니다
    When 제품 이름을 "Updated Product"로 업데이트합니다
    Then 제품이 성공적으로 업데이트되어야 합니다

  Scenario: 존재하지 않는 제품 업데이트
    Given ID가 999인 제품이 시스템에 존재하지 않습니다
    When 존재하지 않는 제품을 업데이트하려고 시도합니다
    Then 업데이트가 발생하지 않아야 합니다

  Scenario: 기존 제품 삭제
    Given ID가 1인 제품이 시스템에 존재합니다
    When ID가 1인 제품을 삭제합니다
    Then 제품이 시스템에서 제거되어야 합니다

  Scenario: 존재하지 않는 제품 삭제
    Given ID가 999인 제품이 시스템에 존재하지 않습니다
    When 존재하지 않는 제품을 삭제하려고 시도합니다
    Then 삭제가 발생하지 않아야 합니다
