ALTER TABLE product ADD (
    category_id BIGINT,
    CONSTRAINT fk_product_category FOREIGN KEY product(category_id) REFERENCES category(id_category)
    ON DELETE SET NULL
    );