CREATE TABLE `shopping_session` (
    `shopping_session_id` BIGINT AUTO_INCREMENT PRIMARY KEY ,
    `user_id` BIGINT NOT NULL,
    `total` DECIMAL(10,2),
    `created_at` TIMESTAMP,
    `modified_at` TIMESTAMP,
    CONSTRAINT FK_shopping_session_user FOREIGN KEY shopping_session(shopping_session_id) references user(id_user)
);

CREATE TABLE `cart_item` (
    `cart_item_id` BIGINT AUTO_INCREMENT PRIMARY KEY ,
    `product_id` BIGINT NOT NULL ,
    `session_id` BIGINT NOT NULL ,
    `unit_price` DECIMAL(10,2) NOT NULL ,
    `quantity` FLOAT NOT NULL,
    CONSTRAINT FK_cart_item_session FOREIGN KEY cart_item (session_id) references shopping_session(shopping_session_id),
    CONSTRAINT FK_cart_item_product FOREIGN KEY cart_item (product_id) references product(id_product)
);

CREATE TABLE `order_details` (
    `order_details_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL ,
    `total` BIGINT NOT NULL ,
    `discounted` BIGINT,
    `final_total` BIGINT NOT NULL ,
    `created_at` BIGINT,
    CONSTRAINT FK_order_details_user FOREIGN KEY order_details(user_id) REFERENCES user(id_user)
);

CREATE TABLE `user_points` (
    `points_id` BIGINT AUTO_INCREMENT PRIMARY KEY ,
    `user_id` BIGINT NOT NULL,
    `points` BIGINT,
    CONSTRAINT FK_user_points_user FOREIGN KEY user_points(user_id) references user(id_user)
);

CREATE TABLE `order_item` (
    `order_item_id` BIGINT AUTO_INCREMENT PRIMARY KEY ,
    `product_id` BIGINT NOT NULL,
    `order_id` BIGINT NOT NULL ,
    `unit_price` DECIMAL(10,2) NOT NULL ,
    `quantity` FLOAT NOT NULL,
    CONSTRAINT FK_order_item_product FOREIGN KEY order_item(product_id) REFERENCES product(id_product),
    CONSTRAINT FK_order_item_order FOREIGN KEY order_item(order_id) REFERENCES order_details(order_details_id)
);