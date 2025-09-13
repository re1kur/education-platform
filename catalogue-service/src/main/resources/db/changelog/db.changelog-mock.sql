--liquibase formatted sql

--changeset re1kur:1
INSERT INTO categories (title, preview_description, description, title_image_id) VALUES
('Electronics', 'Modern electronics and gadgets', 'Wide selection of electronic devices, smartphones, laptops and accessories', 'cdb608dd-32f6-49ed-ba23-06fdd3d5329e'),
('Books', 'Literature for all ages', 'Fiction and educational literature, bestsellers and classics', 'd60d50e9-34be-482e-ab47-e324c4a7866f'),
('Clothing', 'Fashion clothes and accessories', 'Stylish clothes for men, women and children for any season', '613a7789-1543-4e51-be38-93a62e3c70fc');

--changeset re1kur:2
INSERT INTO products (title, price, for_sale, single, description, preview_description, category_id) VALUES
('iPhone 15 Pro', 999, true, true, 'Flagship Apple smartphone with A17 Pro chip', 'New iPhone 15 Pro with titanium body', (select id from categories where title = 'Electronics')),
('Samsung Galaxy S23', 899, true, true, 'Powerful Android smartphone with 200MP camera', 'Galaxy S23 with improved camera', (select id from categories where title = 'Electronics')),
('MacBook Air M2', 1299, true, true, 'Ultrabook from Apple with M2 chip', 'Light and powerful MacBook Air', (select id from categories where title = 'Books')),
('War and Peace', 25, true, false, 'Novel-epic by Leo Tolstoy', 'Classic of Russian literature', (select id from categories where title = 'Books')),
('1984', 20, true, false, 'Dystopia by George Orwell', 'Famous dystopia about totalitarianism', (select id from categories where title = 'Books')),
('Harry Potter and the Philosopher''s Stone', 30, true, false, 'First book about the young wizard', 'Beginning of magical adventure', (select id from categories where title = 'Books')),
('Levi''s 501 Jeans', 80, true, false, 'Classic straight cut jeans', 'Legendary Levi''s jeans', (select id from categories where title = 'Clothing')),
('Nike Dri-FIT T-Shirt', 35, true, false, 'Sports t-shirt with Dry-FIT technology', 'Comfortable t-shirt for workouts', (select id from categories where title = 'Clothing')),
('The North Face Jacket', 200, true, true, 'Warm winter jacket with down filling', 'Reliable protection from cold', (select id from categories where title = 'Clothing'));

--changeset re1kur:3
INSERT INTO product_files (product_id, file_id) VALUES
((SELECT id FROM products WHERE title = 'iPhone 15 Pro'), 'fc5c084e-4ffa-461e-1111-3efeec94cc3c'),
((SELECT id FROM products WHERE title = 'Samsung Galaxy S23'), 'fc5c084e-4ffa-461e-2222-3efeec94cc3c'),
((SELECT id FROM products WHERE title = 'MacBook Air M2'), 'fc5c084e-4ffa-461e-3333-3efeec94cc3c'),
((SELECT id FROM products WHERE title = 'War and Peace'), 'fc5c084e-4ffa-461e-4444-3efeec94cc3c'),
((SELECT id FROM products WHERE title = '1984'), 'fc5c084e-4ffa-461e-5555-3efeec94cc3c');

--changeset re1kur:4
INSERT INTO catalogue_products (product_id, priority) VALUES
((SELECT id FROM products WHERE title = 'iPhone 15 Pro'), 10),
((SELECT id FROM products WHERE title = 'The North Face Jacket'), 8),
((SELECT id FROM products WHERE title = 'War and Peace'), 7),
((SELECT id FROM products WHERE title = '1984'), 5);

--changeset re1kur:5
INSERT INTO orders (id, user_id, status, transaction_id, created_at) VALUES
('11111111-1111-4111-8111-111111111111', '22222222-2222-4222-8222-222222222222', 'SUCCESS', '33333333-3333-4333-8333-333333333333', '2024-01-15 10:30:00'),
('44444444-4444-4444-8444-444444444444', '55555555-5555-4555-8555-555555555555', 'IN_PROCESSING', NULL, '2024-01-20 14:45:00'),
('66666666-6666-4666-8666-666666666666', '77777777-7777-4777-8777-777777777777', 'NEW', NULL, '2024-01-25 09:15:00');

--changeset re1kur:6
INSERT INTO order_products (order_id, product_id) VALUES
('11111111-1111-4111-8111-111111111111', (SELECT id FROM products WHERE title = 'iPhone 15 Pro')),
('44444444-4444-4444-8444-444444444444', (SELECT id FROM products WHERE title = 'The North Face Jacket')),
('66666666-6666-4666-8666-666666666666', (SELECT id FROM products WHERE title = 'War and Peace')),
('66666666-6666-4666-8666-666666666666', (SELECT id FROM products WHERE title = '1984'));

--changeset re1kur:7
INSERT INTO user_products (user_id, product_id, quantity) VALUES
('22222222-2222-4222-8222-222222222222', (SELECT id FROM products WHERE title = 'iPhone 15 Pro'), 1),
('55555555-5555-4555-8555-555555555555', (SELECT id FROM products WHERE title = 'The North Face Jacket'), 1),
('55555555-5555-4555-8555-555555555555', (SELECT id FROM products WHERE title = 'War and Peace'), 2),
('77777777-7777-4777-8777-777777777777', (SELECT id FROM products WHERE title = '1984'), 3);