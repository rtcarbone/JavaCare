CREATE TABLE IF NOT EXISTS appointments
(
    id
    UUID
    PRIMARY
    KEY,
    patient_id
    UUID
    NOT
    NULL,
    user_id
    UUID
    NOT
    NULL,
    appointment_date_time
    TIMESTAMP
    NOT
    NULL,
    status
    VARCHAR
(
    20
) NOT NULL
    );