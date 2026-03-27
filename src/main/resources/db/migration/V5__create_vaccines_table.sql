CREATE TABLE vaccines (

id                  BIGSERIAL       PRIMARY KEY,
pet_id              BIGINT          NOT NULL,
name                VARCHAR(50)     NOT NULL,
laboratory          VARCHAR(50)     NOT NULL,
lote                VARCHAR(50)     NOT NULL,
dose_date           DATE       NOT NULL,
next_dose_date      DATE,
vet_name            VARCHAR(50),
notes               VARCHAR(255),
status              VARCHAR(50)     NOT NULL,
created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
updated_at          TIMESTAMP       NOT NULL DEFAULT NOW(),

CONSTRAINT fk_vaccines_pet FOREIGN KEY (pet_id) REFERENCES pets(id)
);