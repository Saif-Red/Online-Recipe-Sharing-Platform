USE recipehub;

INSERT INTO users (name, email, password_hash, role, bio) VALUES
('Aarav Sharma', 'admin@recipehub.local', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN', 'Platform administrator'),
('Meera Kapoor', 'meera@recipehub.local', 'ca51f29a25456be733c3e7c48861c5e5693fc9d45d51dd5167483adb572a0b7b', 'CONTRIBUTOR', 'Home cook who loves sharing quick recipes'),
('Rohan Verma', 'rohan@recipehub.local', '218ee7b7ec84b477ee83f45cc7867a15b5aa0f232bddeefa499c2e75b42ac34a', 'EXPLORER', 'Always looking for new dinner ideas');

INSERT INTO recipes (author_id, title, description, prep_minutes, cook_minutes, servings, difficulty, cuisine, image_url, status, views) VALUES
(2, 'Creamy Garlic Pasta', 'A simple weeknight pasta with garlic, herbs and a smooth cream sauce.', 10, 20, 2, 'EASY', 'Italian', '/recipehub/images/pasta.svg', 'APPROVED', 128),
(2, 'Masala Vegetable Toast', 'Crispy bread topped with a spicy vegetable mixture and cheese.', 15, 12, 2, 'EASY', 'Indian', '/recipehub/images/toast.svg', 'APPROVED', 94),
(2, 'Chocolate Banana Mug Cake', 'A quick single-serve dessert that is ready in minutes.', 5, 3, 1, 'EASY', 'Dessert', '/recipehub/images/cake.svg', 'PENDING', 41);

INSERT INTO ingredients (recipe_id, item_name, quantity, sort_order) VALUES
(1, 'Pasta', '200 g', 1),
(1, 'Garlic', '4 cloves', 2),
(1, 'Cream', '120 ml', 3),
(1, 'Parmesan', '40 g', 4),
(2, 'Bread slices', '4', 1),
(2, 'Mixed vegetables', '1 cup', 2),
(2, 'Cheese', '60 g', 3),
(3, 'Banana', '1', 1),
(3, 'Cocoa powder', '1 tbsp', 2),
(3, 'Flour', '3 tbsp', 3);

INSERT INTO instructions (recipe_id, step_no, instruction_text) VALUES
(1, 1, 'Boil pasta until just tender and reserve a little pasta water.'),
(1, 2, 'Saute garlic in butter until fragrant.'),
(1, 3, 'Add cream and parmesan, then loosen with pasta water.'),
(1, 4, 'Toss the pasta in the sauce and serve hot.'),
(2, 1, 'Mix chopped vegetables with spices and cheese.'),
(2, 2, 'Spread the mixture over bread slices.'),
(2, 3, 'Toast until crisp and golden.'),
(3, 1, 'Mash banana in a mug and mix in dry ingredients.'),
(3, 2, 'Microwave until the cake is just set.'),
(3, 3, 'Rest for one minute before serving.');

INSERT INTO collections (user_id, name, description) VALUES
(3, 'Weeknight Favourites', 'Recipes that are quick enough for college evenings.');

INSERT INTO collection_items (collection_id, recipe_id) VALUES
(1, 1), (1, 2);

INSERT INTO reviews (recipe_id, user_id, rating, review_text) VALUES
(1, 3, 5, 'Very easy to make and the garlic flavour was great.'),
(2, 3, 4, 'Crispy and tasty. I added a little extra cheese.');

INSERT INTO system_settings (setting_key, setting_value) VALUES
('site_name', 'RecipeHub'),
('recipe_auto_publish', 'false'),
('allow_reviews', 'true');
