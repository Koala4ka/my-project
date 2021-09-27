DROP TABLE IF EXISTS permission_table;
CREATE TABLE permission_table
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) UNIQUE      NOT NULL,
    description VARCHAR(300)            NOT NULL,
    created_at  TIMESTAMP DEFAULT now() NOT NULL,
    updated_at  TIMESTAMP DEFAULT now() NOT NULL
);

