CREATE TABLE income (
    id BIGSERIAL PRIMARY KEY,

    description VARCHAR(50) NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    category_id BIGINT NOT NULL,
    date DATE NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,

    CONSTRAINT fk_income_category FOREIGN KEY (category_id) REFERENCES income_category(id)
);