INSERT INTO users (email, password, first_name, last_name, role)
VALUES
    (
        'user@example.com',
        '$2a$12$IaxCuqD4LH84hCCRbQbiVusueq4Pu3m0XX185PIV01nBvwcHVws1C',
        'Test',
        'User',
        'USER'
    ),
    (
        'admin@example.com',
        '$2a$12$BlFLEyDyf3cvwJ2Ew6E9h.j9qAgTnA.qVz/AB7SaJ8P4BFZLs5bee',
        'Admin',
        'User',
        'ADMIN'
    );
