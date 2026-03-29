-- Convertendo as colunas de Integer para BigInt para agradar o Java
ALTER TABLE medical_records ALTER COLUMN id TYPE BIGINT;
ALTER TABLE prescription_items ALTER COLUMN id TYPE BIGINT;
ALTER TABLE exam_items ALTER COLUMN id TYPE BIGINT;
