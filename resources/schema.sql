DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS menu_items;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id INT AUTO_INCREMENT,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       phone_num VARCHAR(20),
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,

                       city VARCHAR(100),
                       street VARCHAR(150),
                       building_number VARCHAR(20),
                       postal_code VARCHAR(20),
                       details TEXT,
                       points INT DEFAULT 0,

                       restaurant_name VARCHAR(150),
                       is_active BOOLEAN DEFAULT TRUE,
                       access_level VARCHAR(50),

                       vehicle_type VARCHAR(50),
                       is_available BOOLEAN DEFAULT TRUE,
                       rating DOUBLE DEFAULT 0.0,
                       total_deliveries INT DEFAULT 0,

                       PRIMARY KEY (id)
);


CREATE TABLE restaurants (
                             id INT AUTO_INCREMENT,
                             name VARCHAR(150) NOT NULL,
                             address VARCHAR(255) NOT NULL,
                             is_open BOOLEAN DEFAULT FALSE,
                             admin_id INT,

                             PRIMARY KEY (id),
                             CONSTRAINT FK_Restaurant_Admin FOREIGN KEY (admin_id)
                                 REFERENCES users(id) ON DELETE SET NULL
);


CREATE TABLE menus (
                       id INT AUTO_INCREMENT,
                       restaurant_id INT NOT NULL UNIQUE,

                       PRIMARY KEY (id),
                       CONSTRAINT FK_Menu_Restaurant FOREIGN KEY (restaurant_id)
                           REFERENCES restaurants(id) ON DELETE CASCADE
);


CREATE TABLE menu_items (
                            id INT AUTO_INCREMENT,
                            menu_id INT NOT NULL,
                            item_type VARCHAR(50) NOT NULL,
                            name VARCHAR(50),
                            calories DOUBLE,
                            price DOUBLE NOT NULL,
                            description TEXT,
                            is_available BOOLEAN DEFAULT TRUE,
                            estimate_time INT,
                            is_vegan BOOLEAN DEFAULT FALSE,
                            is_vegetarian BOOLEAN DEFAULT FALSE,
                            weight_grams DOUBLE,
                            spiciness_level VARCHAR(50),
                            course_type VARCHAR(50),
                            contains_alcohol BOOLEAN DEFAULT FALSE,
                            alcohol_percentage DOUBLE,
                            volume_ml INT,
                            is_hot BOOLEAN DEFAULT FALSE,

                            PRIMARY KEY (id),
                            CONSTRAINT FK_MenuItem_Menu FOREIGN KEY (menu_id)
                                REFERENCES menus(id) ON DELETE CASCADE
);


CREATE TABLE orders (
                        id INT AUTO_INCREMENT,
                        customer_id INT NOT NULL,
                        restaurant_id INT NOT NULL,
                        driver_id INT,
                        order_date DATETIME NOT NULL,
                        delivery_date DATETIME,
                        status VARCHAR(50) NOT NULL,
                        delivery_fee DOUBLE NOT NULL,
                        subtotal DOUBLE NOT NULL,
                        notes TEXT,

                        PRIMARY KEY (id),
                        CONSTRAINT FK_Order_Customer FOREIGN KEY (customer_id)
                            REFERENCES users(id) ON DELETE CASCADE,
                        CONSTRAINT FK_Order_Restaurant FOREIGN KEY (restaurant_id)
                            REFERENCES restaurants(id) ON DELETE CASCADE,
                        CONSTRAINT FK_Order_Driver FOREIGN KEY (driver_id)
                            REFERENCES users(id) ON DELETE SET NULL
);


CREATE TABLE order_items (
                             order_id INT NOT NULL,
                             menu_item_id INT NOT NULL,
                             quantity INT NOT NULL DEFAULT 1,

                             PRIMARY KEY (order_id, menu_item_id),
                             CONSTRAINT FK_OrderItem_Order FOREIGN KEY (order_id)
                                 REFERENCES orders(id) ON DELETE CASCADE,
                             CONSTRAINT FK_OrderItem_MenuItem FOREIGN KEY (menu_item_id)
                                 REFERENCES menu_items(id) ON DELETE CASCADE
);