-- Inserting Categories
INSERT INTO category (id, name, description)
VALUES (1, 'Basketball', 'Basketball equipment'),
       (2, 'Cycling', 'Cycling equipment'),
       (3, 'Running', 'Running equipment');

-- Inserting Products for each Category
INSERT INTO product (id, name, description, price, category_id)
VALUES (1, 'Basketball', 'Official size basketball', 29.99, 1),
       (2, 'Basketball Shoes', 'Jordan basketball shoes', 99.99, 1),
       (3, 'Cycling Helmet', 'Safety helmet ', 49.99, 2),
       (4, 'Cycling Gloves', 'Gloves for cycling', 19.99, 2),
       (5, 'Running Shoes', 'Comfortable shoes for long-distance running', 79.99, 3),
       (6, 'Running Shorts', 'Breathable shorts for running', 29.99, 3);
