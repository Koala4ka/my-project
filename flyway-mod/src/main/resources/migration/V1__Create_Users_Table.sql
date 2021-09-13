DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    login      VARCHAR(50) UNIQUE      NOT NULL,
    email      VARCHAR(100) UNIQUE     NOT NULL,
    password   VARCHAR(15)             NOT NULL,
    phone      VARCHAR(10) UNIQUE      NOT NULL,
    created_at TIMESTAMP DEFAULT now() NOT NULL,
    updated_at TIMESTAMP DEFAULT now() NOT NULL
);



