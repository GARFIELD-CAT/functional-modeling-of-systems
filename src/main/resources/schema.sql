CREATE TABLE IF NOT EXISTS country (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS purpose_of_visit (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS rule (
    id SERIAL PRIMARY KEY,
    description VARCHAR(2000),
    required_outcome VARCHAR(1000) NOT NULL,
    action_required VARCHAR(1000) NOT NULL,
    period INTEGER NOT NULL,
    purpose_of_visit_id INTEGER,
    countries TEXT[],
    FOREIGN KEY (purpose_of_visit_id) REFERENCES purpose_of_visit(id) ON DELETE CASCADE
);