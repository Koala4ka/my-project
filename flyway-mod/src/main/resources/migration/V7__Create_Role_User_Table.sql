DROP TABLE IF EXISTS role_user;
CREATE TABLE role_user
(
    userId     BIGINT REFERENCES users,
    roleId     BIGINT REFERENCES users,
    created_at TIMESTAMP DEFAULT now() NOT NULL,
    updated_at TIMESTAMP DEFAULT now() NOT NULL,
    PRIMARY KEY (userId, roleId)
);

