CREATE TABLE category (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    depth INT,
    FOREIGN KEY (parent_id) REFERENCES category (category_id)
);

CREATE TABLE product (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    category_id BIGINT,
    -- 다른 필드들...
    FOREIGN KEY (category_id) REFERENCES category (category_id)
);

CREATE TABLE product_qa (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    answered_at TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product (product_id)
);

