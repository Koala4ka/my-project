DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(50) UNIQUE      NOT NULL,
    login      VARCHAR(100) UNIQUE     NOT NULL,
    password   VARCHAR(500)             NOT NULL,
    phone      VARCHAR(13) UNIQUE      NOT NULL,
    created_at TIMESTAMP DEFAULT now() NOT NULL,
    updated_at TIMESTAMP DEFAULT now() NOT NULL
);



