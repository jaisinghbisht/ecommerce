INSERT INTO product (sku, name, price)
VALUES
  ('SKU-001','Sample product A', 9.99),
  ('SKU-002','Sample product B', 19.95)
ON CONFLICT DO NOTHING;