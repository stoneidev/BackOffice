CREATE TABLE category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    depth INT NOT NULL
);

ALTER TABLE category
ADD CONSTRAINT fk_category_parent FOREIGN KEY (parent_id) REFERENCES category (category_id);

CREATE TABLE product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    category_id BIGINT
);

ALTER TABLE product
ADD CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category (category_id);

CREATE TABLE product_qa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    answered_at TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- Category 테이블에 100개의 초기 데이터 삽입
INSERT INTO
    category (name, parent_id, depth)
SELECT
    'Category ' || n,
    CASE
        WHEN n > 1 THEN FLOOR(RAND() * (n - 1) + 1)
        ELSE NULL
    END,
    CASE
        WHEN n > 1 THEN 1
        ELSE 0
    END
FROM (
        SELECT ROW_NUMBER() OVER () AS n
        FROM (
                SELECT 1
                FROM SYSTEM_RANGE (1, 10)
            ) t1, (
                SELECT 1
                FROM SYSTEM_RANGE (1, 10)
            ) t2
        LIMIT 100
    );

-- Product 테이블에 100개의 초기 데이터 삽입
INSERT INTO
    product (product_name, category_id)
SELECT 'Product ' || n, FLOOR(RAND() * 100 + 1)
FROM (
        SELECT ROW_NUMBER() OVER () AS n
        FROM (
                SELECT 1
                FROM SYSTEM_RANGE (1, 10)
            ) t1, (
                SELECT 1
                FROM SYSTEM_RANGE (1, 10)
            ) t2
        LIMIT 100
    );

-- ProductQA 테이블에 100개의 초기 데이터 삽입
INSERT INTO
    product_qa (
        product_id,
        question,
        answer,
        answered_at,
        created_at
    )
SELECT
    FLOOR(RAND() * 100 + 1),
    'Question ' || n,
    CASE
        WHEN RAND() > 0.5 THEN 'Answer ' || n
        ELSE NULL
    END,
    CASE
        WHEN RAND() > 0.5 THEN DATEADD (
            'DAY',
            - FLOOR(RAND() * 365),
            CURRENT_TIMESTAMP
        )
        ELSE NULL
    END,
    DATEADD (
        'DAY',
        - FLOOR(RAND() * 365),
        CURRENT_TIMESTAMP
    )
FROM (
        SELECT ROW_NUMBER() OVER () AS n
        FROM (
                SELECT 1
                FROM SYSTEM_RANGE (1, 10)
            ) t1, (
                SELECT 1
                FROM SYSTEM_RANGE (1, 10)
            ) t2
        LIMIT 100
    );