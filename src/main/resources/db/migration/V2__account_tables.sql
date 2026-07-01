

CREATE TABLE accounts (
    id uuid DEFAULT uuidv7() NOT NULL,
    user_id uuid NOT NULL,
    title VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    category VARCHAR(100) NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE account_additional_information (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    account_id uuid NOT NULL,
    information_type VARCHAR(100) NOT NULL,
    information_value VARCHAR(255) NOT NULL,
    CONSTRAINT pk_account_additional_information PRIMARY KEY (id),
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES accounts (id)
);
