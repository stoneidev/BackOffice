Feature: 제품 상세 정보 조회

  Scenario: 존재하는 제품의 상세 정보 조회
    Given 제품 ID가 1인 제품이 존재합니다
    And 해당 제품에 대한 댓글과 Q&A가 있습니다
    When 제품 ID 1로 상세 정보를 조회합니다
    Then 제품의 이름, 카테고리, 댓글, Q&A 정보를 모두 받아야 합니다

  Scenario: 존재하지 않는 제품의 상세 정보 조회
    Given 제품 ID가 999인 제품이 존재하지 않습니다
    When 제품 ID 999로 상세 정보를 조회합니다
    Then 제품 정보를 찾을 수 없다는 응답을 받아야 합니다
