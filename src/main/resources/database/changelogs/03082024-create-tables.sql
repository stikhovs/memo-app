--liquibase formatted sql
--changeset stikhovs:create-app_user-table
CREATE TABLE IF NOT EXISTS app_user (
	id SERIAL PRIMARY KEY,
	username VARCHAR(100) NOT NULL,
	email VARCHAR(50) NOT NULL UNIQUE,
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp NOT NULL DEFAULT now()
);


--changeset stikhovs:create-card_set-table
CREATE TABLE IF NOT EXISTS card_set (
	id BIGSERIAL PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	uuid UUID NOT NULL,
	user_id int4 NOT NULL REFERENCES app_user (id),
	created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now()
);


--changeset stikhovs:create-card-table
CREATE TABLE IF NOT EXISTS card (
	id BIGSERIAL PRIMARY KEY,
	front_side VARCHAR(100) NOT NULL,
	back_side VARCHAR(100) NOT NULL,
	card_set_id int8 NOT NULL REFERENCES card_set (id),
	created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now()
);