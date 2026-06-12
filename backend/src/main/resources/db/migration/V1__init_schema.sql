CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(50)  NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE products
(
    id          BIGSERIAL PRIMARY KEY,
    sku         VARCHAR(100) UNIQUE NOT NULL,
    name        VARCHAR(255)        NOT NULL,
    description TEXT,
    price       NUMERIC(12, 2)      NOT NULL,
    created_at  TIMESTAMPTZ         NOT NULL DEFAULT now()
);

CREATE TABLE refresh_tokens
(
    id          BIGSERIAL PRIMARY KEY,
    token       VARCHAR(200) UNIQUE NOT NULL,
    expiry_date TIMESTAMPTZ         NOT NULL,
    user_id     BIGINT              NOT NULL REFERENCES users (id) ON DELETE CASCADE
);