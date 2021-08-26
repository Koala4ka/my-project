DROP TABLE IF EXISTS employee;
CREATE TABLE employee
(
    id          BIGSERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    sailingGoal TEXT NOT NULL,
    info        TEXT NOT NULL,
    created_at  TIMESTAMP DEFAULT now(),
    updated_at  TIMESTAMP DEFAULT now()
);

