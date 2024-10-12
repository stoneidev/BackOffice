-- Product 테이블에 audit 컬럼 추가
ALTER TABLE product
ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP,
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);

-- ProductQA 테이블에 audit 컬럼 추가
ALTER TABLE product_qa
ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP,
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);

-- ProductComment 테이블에 audit 컬럼 추가
ALTER TABLE product_comment
ADD COLUMN updated_at TIMESTAMP,
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);

-- Category 테이블에 audit 컬럼 추가
ALTER TABLE category
ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP,
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);
