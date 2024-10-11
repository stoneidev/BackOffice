-- ProductComment 테이블에 샘플 데이터 삽입
INSERT INTO
    product_comment (
        product_id,
        content,
        author,
        created_at
    )
SELECT
    p.product_id,
    CASE
        WHEN RAND() < 0.3 THEN '이 제품은 정말 좋아요! 추천합니다.'
        WHEN RAND() < 0.6 THEN '가격 대비 성능이 훌륭합니다.'
        ELSE '아쉬운 점이 있지만 전반적으로 만족스러워요.'
    END as content,
    CASE
        WHEN RAND() < 0.25 THEN '김철수'
        WHEN RAND() < 0.5 THEN '이영희'
        WHEN RAND() < 0.75 THEN '박지성'
        ELSE '최민지'
    END as author,
    DATEADD (
        'DAY',
        - CAST(RAND() * 365 AS INT),
        CURRENT_TIMESTAMP
    ) as created_at
FROM product p
WHERE
    RAND() < 0.8 -- 80% 확률로 댓글 생성
UNION ALL
SELECT
    p.product_id,
    CASE
        WHEN RAND() < 0.3 THEN '배송이 빨라서 좋았어요.'
        WHEN RAND() < 0.6 THEN '��질이 기대했던 것보다 좋네요.'
        ELSE '사용해보니 몇 가지 단점이 있어요.'
    END as content,
    CASE
        WHEN RAND() < 0.25 THEN '홍길동'
        WHEN RAND() < 0.5 THEN '강나래'
        WHEN RAND() < 0.75 THEN '윤석철'
        ELSE '임수진'
    END as author,
    DATEADD (
        'DAY',
        - CAST(RAND() * 365 AS INT),
        CURRENT_TIMESTAMP
    ) as created_at
FROM product p
WHERE
    RAND() < 0.5 -- 50% 확률로 두 번째 댓글 생성
UNION ALL
SELECT
    p.product_id,
    CASE
        WHEN RAND() < 0.3 THEN '다음에도 이 제품을 구매할 것 같아요.'
        WHEN RAND() < 0.6 THEN '친구들에게도 추천했어요.'
        ELSE '개선되면 좋을 점이 있네요.'
    END as content,
    CASE
        WHEN RAND() < 0.25 THEN '송미란'
        WHEN RAND() < 0.5 THEN '정태우'
        WHEN RAND() < 0.75 THEN '오지은'
        ELSE '류현진'
    END as author,
    DATEADD (
        'DAY',
        - CAST(RAND() * 365 AS INT),
        CURRENT_TIMESTAMP
    ) as created_at
FROM product p
WHERE
    RAND() < 0.3;
-- 30% 확률로 세 번째 댓글 생성