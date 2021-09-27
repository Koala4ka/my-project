DROP TABLE IF EXISTS permission_role;
CREATE TABLE permission_role
(
    role_id     BIGINT REFERENCES roles,
    permission_id     BIGINT REFERENCES permission_table,
    created_at TIMESTAMP DEFAULT now() NOT NULL,
    updated_at TIMESTAMP DEFAULT now() NOT NULL,
    PRIMARY KEY (role_id, permission_id)
);

