CREATE TABLE expense_category (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL UNIQUE,
fixed BOOLEAN,
essential BOOLEAN,

created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP,
created_by BIGINT,
updated_by BIGINT
);
