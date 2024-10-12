Feature: 제품 Q&A 관리

  Scenario: 새로운 QA 추가
    Given ID가 1인 제품이 존재합니다
    When 질문 "How to use this product?"을 가진 새로운 QA를 제품에 추가합니다
    Then QA가 제품에 성공적으로 추가되어야 합니다

  Scenario: 제품의 특정 QA 조회
    Given ID가 1인 제품에 ID가 1인 QA가 있습니다
    When ID가 1인 QA를 제품에서 요청합니다
    Then 정확한 QA 정보를 받아야 합니다

  Scenario: 제품의 모든 QA 조회
    Given ID가 1인 제품에 여러 개의 QA가 있습니다
    When 제품의 모든 QA를 요청합니다
    Then 제품의 모든 QA 목록을 받아야 합니다

  Scenario: QA 업데이트
    Given ID가 1인 제품에 ID가 1인 QA가 있습니다
    When ID가 1인 QA를 새로운 질문 "Is this product durable?"으로 업데이트합니다
    Then QA가 성공적으로 업데이트되어야 합니다

  Scenario: QA 답변
    Given ID가 1인 제품에 ID가 1인 답변되지 않은 QA가 있습니다
    When ID가 1인 QA에 "Yes, it's very durable."으로 답변합니다
    Then QA가 성공적으로 답변되어야 합니다

  Scenario: QA 삭제
    Given ID가 1인 제품에 ID가 1인 QA가 있습니다
    When ID가 1인 QA를 삭제합니다
    Then QA가 제품에서 성공적으로 제거되어야 합니다
