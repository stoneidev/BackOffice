-- 카테고리 추가
INSERT INTO
    category (
        name,
        parent_id,
        depth,
        created_by
    )
VALUES ('전자제품', NULL, 0, 'system'),
    ('의류', NULL, 0, 'system'),
    ('식품', NULL, 0, 'system'),
    ('가구', NULL, 0, 'system'),
    ('도서', NULL, 0, 'system'),
    ('스마트폰', 1, 1, 'system'),
    ('노트북', 1, 1, 'system'),
    ('상의', 2, 1, 'system'),
    ('하의', 2, 1, 'system'),
    ('과일', 3, 1, 'system');

-- 제품 추가
-- 전자제품 > 스마트폰
INSERT INTO
    product (name, category_id, created_by)
VALUES ('갤럭시 S21', 6, 'system'),
    ('아이폰 12', 6, 'system'),
    ('픽셀 5', 6, 'system'),
    ('원플러스 9', 6, 'system'),
    ('샤오미 Mi 11', 6, 'system');

-- 전자제품 > 노트북
INSERT INTO
    product (name, category_id, created_by)
VALUES ('맥북 프로', 7, 'system'),
    ('델 XPS', 7, 'system'),
    ('레노버 ThinkPad', 7, 'system'),
    ('HP 스펙터', 7, 'system'),
    ('에이서 프레데터', 7, 'system');

-- 의류 > 상의
INSERT INTO
    product (name, category_id, created_by)
VALUES ('나이키 드라이핏 티셔츠', 8, 'system'),
    ('아디다스 트레이닝 자켓', 8, 'system'),
    ('유니클로 옥스포드 셔츠', 8, 'system'),
    ('폴로 랄프로렌 폴로 셔츠', 8, 'system'),
    ('노스페이스 플리스 자켓', 8, 'system');

-- 의류 > 하의
INSERT INTO
    product (name, category_id, created_by)
VALUES ('리바이스 501 청바지', 9, 'system'),
    ('나이키 조거 팬츠', 9, 'system'),
    ('아디다스 트레이닝 팬츠', 9, 'system'),
    ('유니클로 치노 팬츠', 9, 'system'),
    ('자라 슬랙스', 9, 'system');

-- 식품 > 과일
INSERT INTO
    product (name, category_id, created_by)
VALUES ('제주 감귤', 10, 'system'),
    ('청송 사과', 10, 'system'),
    ('나주 배', 10, 'system'),
    ('성주 참외', 10, 'system'),
    ('해남 수박', 10, 'system');

-- 제품 댓글 추가
INSERT INTO
    product_comment (product_id, content, author)
VALUES (1, '좋은 제품이에요!', '김철수'),
    (1, '배송이 빨라요', '이영희'),
    (2, '화면이 선명해요', '박지성'),
    (3, '카메라 성능이 훌륭해요', '손흥민');

-- 제품 Q&A 추가
INSERT INTO
    product_qa (product_id, question, answer)
VALUES (
        1,
        '방수 기능이 있나요?',
        'IP68 등급의 방수 기능이 있습니다.'
    ),
    (
        2,
        '배터리 용량이 어떻게 되나요?',
        '3110mAh 용량의 배터리가 탑재되어 있습니다.'
    ),
    (
        3,
        '5G를 지원하나요?',
        '네, 5G를 지원합니다.'
    );