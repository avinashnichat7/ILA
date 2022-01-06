---------
-- DDL --
---------
CREATE TABLE unit(
	id BIGINT NOT NULL,
	code VARCHAR(50) NOT NULL,
	currency VARCHAR(10) NOT NULL,
	decimal_places int NOT NULL,
	time_zone VARCHAR(10) NOT NULL,
	CONSTRAINT unit_pk PRIMARY KEY (id)
);

CREATE TABLE configuration(
	id BIGINT NOT NULL,
	code VARCHAR(50) NOT NULL,
	value VARCHAR(50) NOT NULL,
	unit_id BIGINT NOT NULL,
	description VARCHAR(255),
	CONSTRAINT configuration_pk PRIMARY KEY (id)
);

CREATE TABLE customer_category(
	id BIGINT NOT NULL,
	customer_id VARCHAR(10) NOT NULL,
	name VARCHAR(255) NOT NULL,
	icon VARCHAR(255) NOT NULL,
	color VARCHAR(255) NOT NULL,
	active BOOLEAN NOT NULL,
	created_date TIMESTAMP NOT NULL,
	updated_date TIMESTAMP NOT NULL,
	CONSTRAINT account_pk PRIMARY KEY (id)
);

--FKs
ALTER TABLE configuration ADD CONSTRAINT configuration_code_unique UNIQUE (code);
ALTER TABLE unit ADD CONSTRAINT unit_code_unique UNIQUE (code);

--------------------------------------------
-- Initial Data Records Insert statements --
--------------------------------------------
INSERT INTO unit(id, code, currency, decimal_places, time_zone)
VALUES(1, 'neo', 'BHD', 3, '+03:00');



