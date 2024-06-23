CREATE TABLE IF NOT EXISTS categories
(
    id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) UNIQUE NOT NULL COLLATE pg_catalog."default"
);
