DROP TABLE IF EXISTS goal_table;
CREATE TABLE goal_table
(
    id          BIGSERIAL PRIMARY KEY,
    employee_id BIGINT REFERENCES employee_table ON DELETE CASCADE NOT NULL,
    name        VARCHAR(50) UNIQUE      NOT NULL,
    info        JSONB                   NOT NULL,
    created_at  TIMESTAMP DEFAULT now() NOT NULL,
    updated_at  TIMESTAMP DEFAULT now() NOT NULL

);

