CREATE TABLE category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    depth INT NOT NULL
);

ALTER TABLE category
ADD CONSTRAINT fk_category_parent
FOREIGN KEY (parent_id)
REFERENCES category(category_id);

ALTER TABLE product
ADD CONSTRAINT fk_product_category
FOREIGN KEY (category_id)
REFERENCES category(category_id);