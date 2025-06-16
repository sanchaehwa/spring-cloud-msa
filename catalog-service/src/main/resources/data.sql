INSERT INTO catalog(category_name) VALUES ('Berlin');
INSERT INTO catalog(category_name) VALUES ('Tokyo');
INSERT INTO catalog(category_name) VALUES ('Stockholm');


INSERT INTO products(stock, unit_price,catalog_id, product_name) VALUES (100,1500,1,'product_001');
INSERT INTO products(stock, unit_price, catalog_id, product_name) VALUES (100,900,2,'product_002');
INSERT INTO products(stock, unit_price,catalog_id, product_name) VALUES (100,1200,3,'product_003');

SELECT * FROM catalog;

SELECT * FROM products;

ALTER TABLE products
    MODIFY created_at DATETIME DEFAULT CURRENT_TIMESTAMP;
