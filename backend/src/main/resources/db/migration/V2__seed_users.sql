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
        '$2a$12$FM.9HtoPCnD9Lcf5P8QRxeu.LjNlpCjjwhaukZHAG.ccue.vdPSHu',
        'Admin',
        'User',
        'ADMIN'
    );
