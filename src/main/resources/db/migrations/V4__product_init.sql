CREATE TABLE IF NOT EXISTS products
(
    id SERIAL PRIMARY KEY,
    chef_id integer,
    name character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    category_id integer,
    minimum_quantity integer DEFAULT 1,
    notice_period integer,
    price numeric(10,2),
    status character varying (25)
    deposit_percentage_required integer,
    ingredients character varying(255) COLLATE pg_catalog."default",
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    CONSTRAINT fk_category_id
        FOREIGN KEY (category_id)
        REFERENCES public.categories (id)
        ON DELETE SET NULL
);
