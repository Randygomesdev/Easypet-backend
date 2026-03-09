CREATE TABLE vets (
    id         BIGSERIAL    PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    crmv       VARCHAR(255) NOT NULL UNIQUE,
    specialty  VARCHAR(255) NOT NULL,
    phone      VARCHAR(255) NOT NULL,
    user_id    BIGINT       NOT NULL UNIQUE,
    active     BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP    NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_vets_user FOREIGN KEY (user_id) REFERENCES users(id)
);