CREATE TABLE IF NOT EXISTS users
(
    id
    UUID
    PRIMARY
    KEY,
    full_name
    VARCHAR
(
    100
) NOT NULL, email VARCHAR
(
    100
) NOT NULL UNIQUE, type VARCHAR
(
    20
) NOT NULL );
