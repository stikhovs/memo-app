--liquibase formatted sql
--changeset stikhovs:create-app_user-table runOnChange:true
CREATE TABLE IF NOT EXISTS app_user (
	id SERIAL PRIMARY KEY,
	username VARCHAR(100) NOT NULL,
	email VARCHAR(50) UNIQUE NOT NULL,
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp NOT NULL DEFAULT now()
);

--changeset stikhovs:create-telegram_user-table runOnChange:true
CREATE TABLE IF NOT EXISTS telegram_user (
    id SERIAL PRIMARY KEY,
	username TEXT NOT NULL,
	telegram_user_id BIGINT NOT NULL,
	telegram_chat_id BIGINT NOT NULL,
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp NOT NULL DEFAULT now()
);

--changeset stikhovs:create-composite_user-table runOnChange:true
CREATE TABLE IF NOT EXISTS composite_user (
    id SERIAL PRIMARY KEY,
	app_user_id INTEGER REFERENCES app_user (id),
	telegram_user_id INTEGER REFERENCES telegram_user (id),
	created_at timestamp NOT NULL DEFAULT now(),
	updated_at timestamp NOT NULL DEFAULT now()
);

--changeset stikhovs:create-category-table runOnChange:true
CREATE TABLE IF NOT EXISTS category (
	id BIGSERIAL PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	user_id INTEGER NOT NULL REFERENCES composite_user (id),
	is_default BOOLEAN,
	created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now(),
    UNIQUE (title, user_id)
);

--changeset stikhovs:create-card_set-table runOnChange:true
CREATE TABLE IF NOT EXISTS card_set (
	id BIGSERIAL PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	uuid UUID NOT NULL,
	user_id INTEGER NOT NULL REFERENCES composite_user (id),
	category_id BIGINT NOT NULL REFERENCES category (id),
	created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now()
);


--changeset stikhovs:create-card-table runOnChange:true
CREATE TABLE IF NOT EXISTS card (
	id BIGSERIAL PRIMARY KEY,
	front_side VARCHAR(100) NOT NULL,
	back_side VARCHAR(100) NOT NULL,
	card_set_id BIGINT NOT NULL REFERENCES card_set (id),
	created_at timestamp NOT NULL DEFAULT now(),
    updated_at timestamp NOT NULL DEFAULT now()
);