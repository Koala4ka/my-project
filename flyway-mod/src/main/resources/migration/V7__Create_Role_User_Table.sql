DROP TABLE IF EXISTS role_user;
CREATE TABLE role_user
(
    user_id     BIGINT REFERENCES users,
    role_id     BIGINT REFERENCES users,
    created_at TIMESTAMP DEFAULT now() NOT NULL,
    updated_at TIMESTAMP DEFAULT now() NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

