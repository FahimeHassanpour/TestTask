CREATE TABLE products
(
    id            BIGINT PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    vendor        VARCHAR(255) NOT NULL,
    product_type  VARCHAR(255) NOT NULL,
    price         NUMERIC(10, 2)
)