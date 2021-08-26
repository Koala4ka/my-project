DROP TABLE IF EXISTS organization;
CREATE TABLE organization
(
    id         BIGSERIAL PRIMARY KEY,
    name       TEXT NOT NULL,
    address    TEXT NOT NULL,
    info       TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

