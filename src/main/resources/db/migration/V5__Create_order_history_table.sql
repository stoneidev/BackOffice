CREATE TABLE order_history (
    order_history_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    order_status VARCHAR(50) NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255) NOT NULL
--     FOREIGN KEY (member_id) REFERENCES member (member_id)
);

CREATE INDEX idx_order_history_member_id ON order_history (member_id);
CREATE INDEX idx_order_history_order_id ON order_history (order_id);
