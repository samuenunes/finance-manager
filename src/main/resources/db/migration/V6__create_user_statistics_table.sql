CREATE TABLE user_statistics (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    year INT NOT NULL,
    month INT NOT NULL,
    total_expenses NUMERIC(19,2) DEFAULT 0,
    total_incomes NUMERIC(19,2) DEFAULT 0,
    balance NUMERIC(19,2) DEFAULT 0,
    total_transactions INT DEFAULT 0,
    last_update DATE
);

CREATE TABLE user_expense_category_totals (
    statistics_id BIGINT REFERENCES user_statistics(id),
    category VARCHAR(255),
    amount NUMERIC(19,2),
    PRIMARY KEY (statistics_id, category)
);

CREATE TABLE user_income_category_totals (
    statistics_id BIGINT REFERENCES user_statistics(id),
    category VARCHAR(255),
    amount NUMERIC(19,2),
    PRIMARY KEY (statistics_id, category)
);
