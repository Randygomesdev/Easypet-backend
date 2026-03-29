CREATE TABLE medical_records (
    id SERIAL PRIMARY KEY
    , appointment_id BIGINT NOT NULL UNIQUE
    , diagnosis TEXT NOT NULL
    , general_instructions TEXT
    , created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    , updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    , CONSTRAINT fk_medical_record_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);

CREATE TABLE prescription_items (
    id SERIAL PRIMARY KEY
    , medical_record_id BIGINT NOT NULL
    , medicine_name VARCHAR(255) NOT NULL
    , dosage VARCHAR(255) NOT NULL
    , duration VARCHAR(255) NOT NULL
    , CONSTRAINT fk_prescription_medical_record FOREIGN KEY (medical_record_id) REFERENCES medical_records(id) ON DELETE CASCADE
);

CREATE TABLE exam_items (
    id SERIAL PRIMARY KEY
    , medical_record_id BIGINT NOT NULL
    , exam_name VARCHAR(255) NOT NULL
    , reason TEXT
    , CONSTRAINT fk_exam_medical_record FOREIGN KEY (medical_record_id) REFERENCES medical_records(id) ON DELETE CASCADE
);