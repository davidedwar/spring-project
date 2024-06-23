CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    chef_id INT NOT NULL,
    total_price numeric(10,2) ,
    order_date TIMESTAMP NOT NULL,
    delivery_time TIMESTAMP NOT NULL,
    status TEXT NOT NULL,
    location character varying (100) NOT NULL,
    note TEXT,
    is_delivery BOOLEAN NOT NULL,
    delivery_cost INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id)
);

