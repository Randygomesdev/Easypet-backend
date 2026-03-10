CREATE TABLE appointments (

id                  BIGSERIAL       PRIMARY KEY,
pet_id              BIGINT          NOT NULL,
vet_id              BIGINT          NOT NULL,
type                VARCHAR(50)     NOT NULL,
status              VARCHAR(50)     NOT NULL,
scheduled_at        TIMESTAMP       NOT NULL,
notes               VARCHAR(255),
created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
updated_at          TIMESTAMP       NOT NULL DEFAULT NOW(),

CONSTRAINT fk_appointments_pet FOREIGN KEY (pet_id) REFERENCES pets(id),
CONSTRAINT fk_appointments_vet FOREIGN KEY (vet_id) REFERENCES vets(id)
);