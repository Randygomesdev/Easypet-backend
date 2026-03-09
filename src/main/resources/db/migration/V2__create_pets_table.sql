CREATE TABLE pets (
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    species          VARCHAR(50)  NOT NULL,
    breed            VARCHAR(255) NOT NULL,
    birth_date       DATE         NOT NULL,
    microchip_number VARCHAR(50)  UNIQUE,
    gender           VARCHAR(10)  NOT NULL,
    owner_id         BIGINT       NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP    NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_pets_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);