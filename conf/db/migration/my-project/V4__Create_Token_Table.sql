DROP TABLE IF EXISTS token;
CREATE TABLE token
(
    id                 BIGSERIAL PRIMARY KEY,
    user_id            BIGINT REFERENCES users ON DELETE CASCADE,
    token              TEXT UNIQUE NOT NULL,
    created_at         TIMESTAMP DEFAULT now()
);

