-- ============================================================================
-- Restaurant Management System - Mock Data
-- ============================================================================
-- This script populates the database with sample data for testing
-- Execute this after the Spring Boot application has created the tables
-- ============================================================================

USE restaurantManagement;

-- Clear existing data (in correct order due to foreign key constraints)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE billing;
TRUNCATE TABLE order_item;
TRUNCATE TABLE cart_item;
TRUNCATE TABLE cart;
TRUNCATE TABLE `orders`;
TRUNCATE TABLE menu_item;
TRUNCATE TABLE menu_category;
TRUNCATE TABLE staff;
TRUNCATE TABLE staff_role;
TRUNCATE TABLE dining_table;
TRUNCATE TABLE payment_method;
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================================
-- 1. STAFF ROLES
-- ============================================================================
INSERT INTO staff_role (role_name, role_description) VALUES
('Manager', 'Restaurant manager with full access'),
('Waiter', 'Serves customers and takes orders'),
('Chef', 'Prepares food in the kitchen'),
('Bartender', 'Prepares beverages'),
('Host', 'Greets and seats customers'),
('Cashier', 'Handles payments and billing');

-- ============================================================================
-- 2. STAFF
-- ============================================================================
INSERT INTO staff (first_name, last_name, contact_number, email, role_id) VALUES
('John', 'Smith', '555-0101', 'john.smith@restaurant.com', 1),
('Sarah', 'Johnson', '555-0102', 'sarah.johnson@restaurant.com', 2),
('Michael', 'Brown', '555-0103', 'michael.brown@restaurant.com', 3),
('Emily', 'Davis', '555-0104', 'emily.davis@restaurant.com', 2),
('David', 'Wilson', '555-0105', 'david.wilson@restaurant.com', 3),
('Lisa', 'Anderson', '555-0106', 'lisa.anderson@restaurant.com', 4),
('James', 'Taylor', '555-0107', 'james.taylor@restaurant.com', 2),
('Jennifer', 'Martinez', '555-0108', 'jennifer.martinez@restaurant.com', 5),
('Robert', 'Garcia', '555-0109', 'robert.garcia@restaurant.com', 6);

-- ============================================================================
-- 3. PAYMENT METHODS
-- ============================================================================
INSERT INTO payment_method (method_name, method_description) VALUES
('Cash', 'Payment in cash'),
('Credit Card', 'Visa, Mastercard, Amex'),
('Debit Card', 'Debit card payment'),
('Mobile Payment', 'Apple Pay, Google Pay, etc.'),
('Gift Card', 'Restaurant gift cards'),
('Digital Wallet', 'PayPal, Venmo, etc.');

-- ============================================================================
-- 4. DINING TABLES
-- ============================================================================
INSERT INTO dining_table (table_number, seating_capacity, location) VALUES
-- Main Hall
(1, 2, 'Main Hall'),
(2, 2, 'Main Hall'),
(3, 4, 'Main Hall'),
(4, 4, 'Main Hall'),
(5, 6, 'Main Hall'),
(6, 4, 'Main Hall'),
(7, 2, 'Main Hall'),
(8, 4, 'Main Hall'),
-- Patio
(9, 4, 'Patio'),
(10, 2, 'Patio'),
(11, 6, 'Patio'),
(12, 4, 'Patio'),
-- VIP Section
(13, 8, 'VIP'),
(14, 6, 'VIP'),
(15, 4, 'VIP'),
-- Bar Area
(16, 2, 'Bar Area'),
(17, 2, 'Bar Area'),
(18, 4, 'Bar Area');

-- ============================================================================
-- 5. MENU CATEGORIES
-- ============================================================================
INSERT INTO menu_category (category_name, category_description) VALUES
('Appetizers', 'Start your meal with our delicious starters'),
('Soups & Salads', 'Fresh salads and hearty soups'),
('Main Course', 'Our signature main dishes'),
('Pasta & Rice', 'Italian favorites and rice dishes'),
('Seafood', 'Fresh from the ocean'),
('Grilled Specialties', 'Expertly grilled to perfection'),
('Vegetarian', 'Delicious meat-free options'),
('Desserts', 'Sweet endings to your meal'),
('Beverages', 'Refreshing drinks and cocktails'),
('Kids Menu', 'Kid-friendly meals');

-- ============================================================================
-- 6. MENU ITEMS
-- ============================================================================

-- Appetizers
INSERT INTO menu_item (name, description, price, availability_status, category_id) VALUES
('Bruschetta', 'Grilled bread topped with fresh tomatoes, basil, and mozzarella', 8.99, true, 1),
('Mozzarella Sticks', 'Crispy fried mozzarella with marinara sauce', 7.99, true, 1),
('Chicken Wings', '10 pieces with choice of BBQ, Buffalo, or Honey Garlic sauce', 12.99, true, 1),
('Calamari Fritti', 'Lightly breaded and fried calamari rings', 11.99, true, 1),
('Spring Rolls', 'Vegetable spring rolls with sweet chili sauce', 6.99, true, 1),
('Garlic Bread', 'Toasted bread with garlic butter and herbs', 5.99, true, 1),
('Nachos Supreme', 'Tortilla chips with cheese, jalapeños, sour cream, and guacamole', 10.99, true, 1);

-- Soups & Salads
INSERT INTO menu_item (name, description, price, availability_status, category_id) VALUES
('Caesar Salad', 'Romaine lettuce, parmesan, croutons, Caesar dressing', 9.99, true, 2),
('Greek Salad', 'Tomatoes, cucumber, olives, feta cheese, olive oil', 10.99, true, 2),
('Tomato Soup', 'Classic tomato soup with fresh basil', 6.99, true, 2),
('Chicken Noodle Soup', 'Homemade chicken soup with vegetables and noodles', 7.99, true, 2),
('Garden Salad', 'Mixed greens with seasonal vegetables', 8.99, true, 2),
('French Onion Soup', 'Caramelized onions with melted cheese and croutons', 8.99, true, 2);

-- Main Course
INSERT INTO menu_item (name, description, price, availability_status, category_id) VALUES
('Ribeye Steak', '12oz USDA Prime ribeye with mashed potatoes and vegetables', 34.99, true, 3),
('Grilled Chicken Breast', 'Herb-marinated chicken with roasted vegetables', 18.99, true, 3),
('Roasted Lamb Chops', 'New Zealand lamb chops with mint sauce', 32.99, true, 3),
('BBQ Ribs', 'Fall-off-the-bone pork ribs with coleslaw and fries', 24.99, true, 3),
('Beef Tenderloin', '8oz tenderloin with red wine reduction', 38.99, true, 3),
('Chicken Parmesan', 'Breaded chicken with marinara and mozzarella', 19.99, true, 3);

-- Pasta & Rice
INSERT INTO menu_item (name, description, price, availability_status, category_id) VALUES
('Spaghetti Carbonara', 'Classic carbonara with bacon, eggs, and parmesan', 16.99, true, 4),
('Fettuccine Alfredo', 'Creamy alfredo sauce with fettuccine pasta', 15.99, true, 4),
('Penne Arrabbiata', 'Spicy tomato sauce with penne pasta', 14.99, true, 4),
('Seafood Risotto', 'Creamy risotto with shrimp, scallops, and mussels', 22.99, true, 4),
('Lasagna', 'Layered pasta with meat sauce and cheese', 17.99, true, 4),
('Mushroom Risotto', 'Creamy risotto with porcini mushrooms', 18.99, true, 4);

-- Seafood
INSERT INTO menu_item (name, description, price, availability_status, category_id) VALUES
('Grilled Salmon', 'Atlantic salmon with lemon butter sauce', 26.99, true, 5),
('Fish and Chips', 'Beer-battered cod with fries and tartar sauce', 16.99, true, 5),
('Lobster Tail', 'Grilled lobster tail with drawn butter', 42.99, true, 5),
('Shrimp Scampi', 'Garlic butter shrimp with white wine sauce', 24.99, true, 5),
('Seafood Platter', 'Mix of grilled fish, shrimp, calamari, and mussels', 36.99, true, 5),
('Tuna Steak', 'Seared ahi tuna with sesame crust', 28.99, true, 5);

-- Grilled Specialties
INSERT INTO menu_item (name, description, price, availability_status, category_id) VALUES
('Mixed Grill', 'Combination of chicken, beef, and lamb', 29.99, true, 6),
('Pork Chops', 'Two thick-cut pork chops with apple sauce', 21.99, true, 6),
('Grilled Vegetables', 'Seasonal vegetables grilled to perfection', 13.99, true, 6),
('BBQ Chicken', 'Half chicken with BBQ sauce and corn on the cob', 19.99, true, 6);

-- Vegetarian
INSERT INTO menu_item (name, description, price, availability_status, category_id) VALUES
('Vegetable Curry', 'Mixed vegetables in coconut curry sauce with rice', 15.99, true, 7),
('Veggie Burger', 'House-made veggie patty with avocado and fries', 14.99, true, 7),
('Eggplant Parmesan', 'Breaded eggplant with marinara and mozzarella', 16.99, true, 7),
('Stuffed Bell Peppers', 'Peppers filled with quinoa, vegetables, and cheese', 15.99, true, 7),
('Vegetable Stir Fry', 'Asian-style vegetables with tofu and rice', 14.99, true, 7);

-- Desserts
INSERT INTO menu_item (name, description, price, availability_status, category_id) VALUES
('Tiramisu', 'Classic Italian coffee-flavored dessert', 8.99, true, 8),
('Chocolate Lava Cake', 'Warm chocolate cake with molten center and vanilla ice cream', 9.99, true, 8),
('Cheesecake', 'New York style cheesecake with berry compote', 8.99, true, 8),
('Crème Brûlée', 'Vanilla custard with caramelized sugar top', 8.99, true, 8),
('Ice Cream Sundae', 'Three scoops with toppings and whipped cream', 7.99, true, 8),
('Apple Pie', 'Homemade apple pie with vanilla ice cream', 7.99, true, 8),
('Brownie Delight', 'Warm brownie with ice cream and chocolate sauce', 8.99, true, 8);

-- Beverages
INSERT INTO menu_item (name, description, price, availability_status, category_id) VALUES
('Coca-Cola', 'Classic Coke', 2.99, true, 9),
('Fresh Orange Juice', 'Freshly squeezed orange juice', 4.99, true, 9),
('Iced Tea', 'Sweetened or unsweetened', 2.99, true, 9),
('Coffee', 'Freshly brewed coffee', 2.99, true, 9),
('Espresso', 'Single or double shot', 3.99, true, 9),
('Cappuccino', 'Espresso with steamed milk and foam', 4.99, true, 9),
('Lemonade', 'Homemade fresh lemonade', 3.99, true, 9),
('Mojito', 'Classic mojito with mint and lime', 8.99, true, 9),
('Margarita', 'Classic lime margarita', 9.99, true, 9),
('House Wine', 'Red or White wine by the glass', 7.99, true, 9),
('Draft Beer', 'Selection of local and imported beers', 5.99, true, 9);

-- Kids Menu
INSERT INTO menu_item (name, description, price, availability_status, category_id) VALUES
('Kids Burger', 'Mini burger with fries', 8.99, true, 10),
('Chicken Nuggets', '6 pieces with fries and dipping sauce', 7.99, true, 10),
('Mac and Cheese', 'Creamy macaroni and cheese', 6.99, true, 10),
('Kids Pasta', 'Spaghetti with butter or marinara sauce', 6.99, true, 10),
('Grilled Cheese', 'Classic grilled cheese sandwich with fries', 6.99, true, 10),
('Kids Pizza', 'Personal cheese or pepperoni pizza', 7.99, true, 10);

-- ============================================================================
-- 7. SAMPLE ORDERS (Optional - for testing order/table features)
-- ============================================================================

-- Sample Order 1 - Table 3
INSERT INTO orders (order_time, status, table_id, staff_id) VALUES
(NOW(), 'CLOSED', 3, 2);

SET @order1_id = LAST_INSERT_ID();

INSERT INTO order_item (quantity, item_price_at_order_time, order_id, item_id) VALUES
(2, 8.99, @order1_id, 1),
(1, 18.99, @order1_id, 14),
(1, 8.99, @order1_id, 46);

INSERT INTO billing (billing_time, total_amount, discount, tax, final_amount, payment_status, order_id, payment_method_id) VALUES
(NOW(), 45.97, 0.00, 4.14, 50.11, 'PAID', @order1_id, 2);

-- Sample Order 2 - Table 5
INSERT INTO orders (order_time, status, table_id, staff_id) VALUES
(NOW(), 'PREPARING', 5, 4);

SET @order2_id = LAST_INSERT_ID();

INSERT INTO order_item (quantity, item_price_at_order_time, order_id, item_id) VALUES
(2, 26.99, @order2_id, 27),
(1, 24.99, @order2_id, 28),
(2, 7.99, @order2_id, 46);

-- Sample Order 3 - Table 9 (Patio)
INSERT INTO orders (order_time, status, table_id, staff_id) VALUES
(NOW(), 'SERVED', 9, 2);

SET @order3_id = LAST_INSERT_ID();

INSERT INTO order_item (quantity, item_price_at_order_time, order_id, item_id) VALUES
(1, 34.99, @order3_id, 13),
(1, 16.99, @order3_id, 21),
(1, 10.99, @order3_id, 9);

INSERT INTO billing (billing_time, total_amount, discount, tax, final_amount, payment_status, order_id, payment_method_id) VALUES
(NOW(), 62.97, 5.00, 5.67, 63.64, 'PAID', @order3_id, 1);

-- ============================================================================
-- VERIFICATION QUERIES
-- ============================================================================
-- Run these to verify the data was inserted correctly

-- SELECT COUNT(*) as staff_roles FROM staff_role;
-- SELECT COUNT(*) as staff FROM staff;
-- SELECT COUNT(*) as payment_methods FROM payment_method;
-- SELECT COUNT(*) as tables FROM dining_table;
-- SELECT COUNT(*) as categories FROM menu_category;
-- SELECT COUNT(*) as menu_items FROM menu_item;
-- SELECT COUNT(*) as orders FROM orders;
-- SELECT COUNT(*) as order_items FROM order_item;
-- SELECT COUNT(*) as bills FROM billing;

-- View menu summary
-- SELECT 
--     mc.category_name,
--     COUNT(mi.item_id) as item_count,
--     MIN(mi.price) as min_price,
--     MAX(mi.price) as max_price,
--     AVG(mi.price) as avg_price
-- FROM menu_category mc
-- LEFT JOIN menu_item mi ON mc.category_id = mi.category_id
-- GROUP BY mc.category_id, mc.category_name;

-- ============================================================================
-- END OF MOCK DATA SCRIPT
-- ============================================================================
