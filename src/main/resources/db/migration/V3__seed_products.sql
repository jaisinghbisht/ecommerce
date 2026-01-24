INSERT INTO products (sku, name, description, price)
SELECT
    'SKU-' || gs,
    'Product ' || gs,
    'Auto generated product ' || gs,
    (random() * 100)::numeric(12,2)
FROM generate_series(1, 500) gs
    ON CONFLICT (sku) DO NOTHING;