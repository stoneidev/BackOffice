-- 카테고리 테이블 생성
CREATE TABLE category (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    depth INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    FOREIGN KEY (parent_id) REFERENCES category(category_id)
);

-- 제품 테이블 생성
CREATE TABLE product (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    category_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);

-- 제품 댓글 테이블 생성
CREATE TABLE product_comment (
    comment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    content TEXT NOT NULL,
    author VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- 제품 Q&A 테이블 생성
CREATE TABLE product_qa (
    qa_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    question TEXT NOT NULL,
    answer TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);
