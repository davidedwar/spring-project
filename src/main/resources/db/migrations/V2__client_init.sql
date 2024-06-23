CREATE TABLE IF NOT EXISTS client (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(255) UNIQUE,
    password VARCHAR(60) NOT NULL,
    status VARCHAR(255) NOT NULL,
    date_created TIMESTAMP,
    date_last_action TIMESTAMP,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    language VARCHAR(255),
    sex BOOLEAN,
    birth_date DATE
);
