DROP TABLE IF EXISTS employee_table;
CREATE TABLE employee_table
(
    id              BIGSERIAL PRIMARY KEY,
    organization_id BIGINT REFERENCES organization ON DELETE CASCADE NOT NULL,
    name            TEXT NOT NULL,
    info            TEXT NOT NULL,
    created_at      TIMESTAMP DEFAULT now() NOT NULL,
    updated_at      TIMESTAMP DEFAULT now() NOT NULL
);

