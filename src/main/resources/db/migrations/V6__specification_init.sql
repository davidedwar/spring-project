CREATE TABLE IF NOT EXISTS specifications
(
    id SERIAL PRIMARY KEY,
    name character varying(255) COLLATE pg_catalog."default",
    additional_cost numeric(10,2) DEFAULT 0.00,
    description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);
