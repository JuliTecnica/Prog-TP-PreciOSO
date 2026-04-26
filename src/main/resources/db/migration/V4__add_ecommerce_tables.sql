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