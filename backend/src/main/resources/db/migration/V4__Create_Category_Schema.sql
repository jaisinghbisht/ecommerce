-- Create the categories table
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_category_name UNIQUE (name)
);

-- Add the category_id column to the products table
ALTER TABLE products
ADD COLUMN category_id BIGINT;

-- Add a foreign key constraint to link products to categories
ALTER TABLE products
ADD CONSTRAINT fk_product_category
FOREIGN KEY (category_id)
REFERENCES categories(id)
ON DELETE SET NULL; -- If a category is deleted, set the product's category_id to NULL

-- Create an index on the new foreign key for performance
CREATE INDEX idx_product_category_id ON products(category_id);
