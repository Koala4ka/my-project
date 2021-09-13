DROP TABLE IF EXISTS auth_token;
CREATE TABLE auth_token
(
    id         BIGSERIAL PRIMARY KEY                     NOT NULL,
    user_id    BIGINT REFERENCES users ON DELETE CASCADE NOT NULL,
    token      TEXT UNIQUE                               NOT NULL,
    created_at TIMESTAMP DEFAULT now()                   NOT NULL
);

