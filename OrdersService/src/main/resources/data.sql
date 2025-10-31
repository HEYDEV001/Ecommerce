INSERT INTO orders(total_price, order_status)VALUES
(100.50, 'PENDING'),
(249.99, 'APPROVED'),
(89.00, 'REJECTED'),
(320.00, 'PENDING'),
(150.25, 'APPROVED'),
(275.00, 'REJECTED'),
(400.10, 'APPROVED'),
(99.99, 'PENDING'),
(500.00, 'REJECTED'),
(125.75, 'APPROVED');

INSERT INTO order_item(order_id, product_id, quantity) VALUES
(1, 101, 2),
(1, 102, 1),
(2, 103, 3),
(3, 104, 1),
(4, 105, 2),
(5, 106, 4),
(6, 107, 1),
(7, 108, 3),
(8, 109, 2),
(9, 110, 5),
(10, 111, 1);