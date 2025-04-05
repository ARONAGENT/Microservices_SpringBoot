-- Orders
INSERT INTO orders (order_status, total_price) VALUES
('PENDING', 65.47),       -- ID 1: (19.99*2 + 25.49)
('CONFIRMED', 59.99),     -- ID 2: (59.99)
('DELIVERED', 294.93),    -- ID 3: (129.99*2 + 34.95)
('CANCELLED', 75.00),     -- ID 4: (75.00)
('PENDING', 86.96),       -- ID 5: (8.99*3 + 59.99)
('DELIVERED', 30.49),     -- ID 6: (2.50*5 + 15.99)
('DELIVERED', 45.00);     -- ID 7: (45.00)

-- Order Items
INSERT INTO order_item (prod_id, quantity, order_id) VALUES
(1, 2, 1),  -- 19.99*2 = 39.98
(3, 1, 1),  -- 25.49*1 = 25.49
(2, 1, 2),  -- 59.99*1
(5, 2, 3),  -- 129.99*2 = 259.98
(4, 1, 3),  -- 34.95
(6, 1, 4),  -- 75.00
(7, 3, 5),  -- 8.99*3 = 26.97
(2, 1, 5),  -- 59.99
(8, 5, 6),  -- 2.50*5 = 12.50
(9, 1, 6),  -- 15.99
(10, 1, 7); -- 45.00
