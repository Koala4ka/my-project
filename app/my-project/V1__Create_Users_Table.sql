DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    login      TEXT UNIQUE   NOT NULL,
    email      TEXT UNIQUE   NOT NULL,
    password   TEXT          NOT NULL,
    phone      BIGINT UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

