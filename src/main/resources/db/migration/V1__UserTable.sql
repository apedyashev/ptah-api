CREATE TABLE users (
    id      UUID NOT NULL PRIMARY KEY,
    login   VARCHAR(15),
    email   VARCHAR(20)
);