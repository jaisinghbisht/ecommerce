INSERT INTO
    users (email, password, role, created_at)
VALUES
    (
        'user1@example.com',
        '$2a$12$TimGq6yvA2PwBNuklg89ReCJr6ZxzfmDbh7C12HF8LuP9jT5/vzL.',
        'USER',
        NOW ()
    ),
    (
        'user2@example.com',
        '$2a$12$tElfuzVV1Vhq5cKzWZ4qKeYalYpSiQnqq9u.wCIDW.2LxvQWQEr2e',
        'ADMIN',
        NOW ()
    );