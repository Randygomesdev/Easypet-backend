ALTER TABLE appointments RENAME COLUMN type TO appointment_type;
ALTER TABLE appointments RENAME COLUMN status TO appointment_status;

UPDATE appointments SET appointment_type = 'VET_CONSULTATION' WHERE appointment_type = 'CONSULTATION';
UPDATE appointments SET appointment_type = 'GROOMING' WHERE appointment_type = 'BATH';
UPDATE appointments SET appointment_type = 'GROOMING' WHERE appointment_type = 'BATH_AND_GROOMING';



