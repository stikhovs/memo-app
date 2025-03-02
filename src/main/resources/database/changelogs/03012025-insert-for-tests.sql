--changeset stikhovs:insert-test-user
INSERT INTO app_user (username, email, created_at, updated_at)
VALUES('test user', 'test@test.com', now(), now());

--changeset stikhovs:insert-test-card-set
INSERT INTO card_set (title, uuid, user_id, created_at, updated_at)
VALUES('test card set', 'fa89840d-44ba-455e-91ce-589f3b3a7b24', 1, now(), now());

--changeset stikhovs:insert-test-cards
INSERT INTO card (front_side, back_side, card_set_id, created_at, updated_at)
VALUES
    ('front 1', 'back 1', 1, now(), now()),
    ('front 2', 'back 2', 1, now(), now()),
    ('front 3', 'back 3', 1, now(), now()),
    ('front 4', 'back 4', 1, now(), now()),
    ('front 5', 'back 5', 1, now(), now());
