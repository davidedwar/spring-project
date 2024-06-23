CREATE TABLE IF NOT EXISTS product_specifications
(
    id SERIAL PRIMARY KEY,
    product_id integer,
    specification_id integer,
    additional_cost numeric(10,2), -- This holds the extra cost for the specification.
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    CONSTRAINT fk_product_id
        FOREIGN KEY (product_id)
        REFERENCES public.products (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_specification_id
        FOREIGN KEY (specification_id)
        REFERENCES public.specifications (id)
        ON DELETE CASCADE
);
