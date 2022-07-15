CREATE TABLE IF NOT EXISTS customers
(
    id         uuid PRIMARY KEY NOT NULL,
    username   VARCHAR UNIQUE   NOT NULL,
    first_name VARCHAR          NOT NULL,
    last_name  VARCHAR          NOT NULL
);