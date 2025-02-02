CREATE TABLE daily_offers (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL UNIQUE,
    new_price NUMERIC(10, 2) NOT NULL,
    chef_id INTEGER,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
    FOREIGN KEY (chef_id) REFERENCES chef(id)
);
