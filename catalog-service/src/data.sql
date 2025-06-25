#테이블 드롭
CREATE TABLE catalog (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,               -- 내부용 PK
                         product_id VARCHAR(50) UNIQUE NOT NULL,             -- 외부 식별용 ID (CATALOG-0001 등)
                         product_name VARCHAR(100) NOT NULL,
                         stock INT NOT NULL,
                         unit_price INT NOT NULL,
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO catalog(product_id, product_name, stock, unit_price)
VALUES
     ("CATALOG-0001", "Berlin", 100, 1500),
     ("CATALOG-0002", "Tokyo", 100, 900),
     ("CATALOG-0003", "Stockholm", 100, 1200);
SELECT * FROM catalog;
