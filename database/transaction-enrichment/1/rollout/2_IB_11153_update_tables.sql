CREATE TABLE customer_merchant_category(
	id BIGINT NOT NULL,
	customer_id VARCHAR(10) NOT NULL,
	name VARCHAR(255) NOT NULL,
	category_id VARCHAR(10) NOT NULL,
	isCustom BOOLEAN NOT NULL,
	active BOOLEAN NOT NULL,
	created_date TIMESTAMP NOT NULL,
	updated_date TIMESTAMP NOT NULL,
	CONSTRAINT customer_merchant_category_pk PRIMARY KEY (id)
);

CREATE TABLE customer_account_transaction_category(
	id BIGINT NOT NULL,
	customer_id VARCHAR(10) NOT NULL,
    transaction_reference VARCHAR(255) NOT NULL,
    account_id VARCHAR(255) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    category_id VARCHAR(10) NOT NULL,
    isCustom BOOLEAN NOT NULL,
    active BOOLEAN NOT NULL,
	created_date TIMESTAMP NOT NULL,
	updated_date TIMESTAMP NOT NULL,
	CONSTRAINT customer_account_transaction_category_pk PRIMARY KEY (id)
);

CREATE TABLE customer_credit_transaction_category(
	id BIGINT NOT NULL,
    customer_id VARCHAR(10) NOT NULL,
    transaction_reference VARCHAR(255) NOT NULL,
    account_id VARCHAR(255) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    category_id VARCHAR(10) NOT NULL,
    isCustom BOOLEAN NOT NULL,
    active BOOLEAN NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_date TIMESTAMP NOT NULL,
	CONSTRAINT customer_credit_transaction_category_pk PRIMARY KEY (id)
);

ALTER TABLE customer_category ADD COLUMN icon_label_url VARCHAR(10);

-- Index
CREATE INDEX idx_mer_customerId_active ON customer_merchant_category(customer_id, active);
CREATE INDEX idx_acc_customerId_active ON customer_account_transaction_category(customer_id, active);
CREATE INDEX idx_cre_customerId_active ON customer_credit_transaction_category(customer_id, active);