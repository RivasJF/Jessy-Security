CREATE TABLE users
(
    id            uuid DEFAULT gen_random_uuid() NOT NULL,
    username      VARCHAR(30)                    NOT NULL,
    email         VARCHAR(100)                   NOT NULL,
    password_hash VARCHAR(255)                   NOT NULL,
    salt_public   VARCHAR(255)                   NOT NULL,
    created_at    TIMESTAMPTZ                    NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT pk_email UNIQUE (email)
);

-- CREATE TABLE transaction_types
-- (
--     id         BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
--     name       VARCHAR(30)                         NOT NULL,
--     created_at TIMESTAMPTZ                         NOT NULL,
--     deleted_at TIMESTAMPTZ,
--     CONSTRAINT pk_id PRIMARY KEY (id)
-- );
--
-- CREATE TYPE transaction_category AS ENUM (
--     'EXPENSE',
--     'INCOME',
--     'LENT',
--     'DEBT'
--     );
--
-- CREATE TABLE transactions
-- (
--     id                   uuid DEFAULT gen_random_uuid() NOT NULL,
--     user_id              uuid                           NOT NULL,
--     amount               DECIMAL(8, 2)                 NOT NULL,
--     transaction_category transaction_category           NOT NULL,
--     description          VARCHAR(100),
--     transaction_type_id  BIGINT,
--     created_at           TIMESTAMPTZ                    NOT NULL,
--     CONSTRAINT pk_transactions PRIMARY KEY (id),
--     CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id),
--     CONSTRAINT fk_transaction_type FOREIGN KEY (transaction_type_id) REFERENCES transaction_types (id)
-- );