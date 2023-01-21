DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS users;

CREATE TABLE items(
                      id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      name varchar(255),
                      description varchar(255),
                      owner bigint,
                      available boolean
);

CREATE TABLE users(
                      id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      name varchar(255),
                      email varchar(255)
);

ALTER TABLE items ADD FOREIGN KEY (owner) REFERENCES users (id) ON DELETE CASCADE;