CREATE TABLE products
(
    id          BIGINT(20) AUTO_INCREMENT,
    name        VARCHAR(50)   NOT NULL,
    price       NUMERIC NOT NULL,
    category    VARCHAR(50),
    discount    NUMERIC DEFAULT NULL,
    count       INT,
    description VARCHAR(250)   DEFAULT NULL,
--     cart_id     BIGINT(20),
    PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id   BIGINT(20) AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
--     product_id BIGINT(20),
    PRIMARY KEY (id)
);

CREATE TABLE product_cart
(
    product_id BIGINT(20),
    cart_id    INT(20),
    PRIMARY KEY (product_id, cart_id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (cart_id) REFERENCES cart (id)
);

INSERT INTO products (name, price, category, count, discount, description)
VALUES ('Kefirs', 0.99, 'MILK', 100, 10, 'Valmieras'),
       ('Salmon', 10.0, 'FISH', 100, 15, 'fresh'),
       ('Pork', 5.0, 'MEAT', 100, 0, ''),
       ('Apple', 20.0, 'FRUITS', 100, 0, 'Polish'),
       ('Milk', 1.0, 'MILK', 100, 50, 'Greek'),
       ('Watermelon', 10.0, 'FRUITS', 100, 56, 'Greek'),
       ('Biscuits', 8.0, 'SWEETS', 100, 5, 'chocolate'),
       ('Cola', 10.0, 'BEVERAGE', 100, 0, 'zero'),
       ('Water', 10.0, 'BEVERAGE', 100, 0, 'Sparkling'),
       ('Candle', 5.0, 'HOUSE', 100, 15, 'Greek'),
       ('Ice-cream', 0.5, 'FROZEN', 100, 20, 'with nuts'),
       ('Nesquick', 2.0, 'CEREALS', 100, 12, 'discount'),
       ('Toilet paper', 5.0, 'OTHER', 100, 25, ''),
       ('Potatoes', 0.5, 'VEGETABLES', 100, 13, 'Latvian'),
       ('Grapes', 3.0, 'FRUITS', 100, 15, 'Greek'),
       ('Wine', 15.0, 'ALCOHOL', 100, 10, 'Primitivo');

INSERT INTO cart (name)
VALUES ('cart1'),
       ('cart2');

INSERT INTO product_cart (product_id, cart_id)
VALUES (1, 2),
       (1, 1),
       (2, 1),
       (2, 2);