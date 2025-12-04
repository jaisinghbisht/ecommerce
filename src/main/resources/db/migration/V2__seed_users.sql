INSERT INTO users (email, password_hash, role, created_at)
VALUES
  ('user1@example.com', 'hashed_password1', 'USER', NOW()),
  ('user2@example.com', 'hashed_password2', 'ADMIN', NOW());