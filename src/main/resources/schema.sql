DROP TABLE IF EXISTS items cascade;
DROP TABLE IF EXISTS users cascade;
DROP TABLE IF EXISTS bookings cascade;
DROP TABLE IF EXISTS comments cascade;

CREATE TABLE items
(
    id          bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        varchar(128),
    description varchar(255),
    owner       bigint,
    available   boolean
);

CREATE TABLE users
(
    id    bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name  varchar(64),
    email varchar(64),
        constraint un_user_email unique (email)
);

CREATE TABLE bookings
(
    id         bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    start_date TIMESTAMP WITHOUT TIME ZONE,
    end_date   TIMESTAMP WITHOUT TIME ZONE,
    item_id    bigint,
    booker_id  bigint,
    status     varchar(32)
);

CREATE TABLE comments
(
    id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    text      varchar(255),
    item_id   bigint,
    author_id bigint
);

ALTER TABLE items
    ADD FOREIGN KEY (owner) REFERENCES users (id) ON DELETE CASCADE;
ALTER TABLE bookings
    ADD FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE;
ALTER TABLE bookings
    ADD FOREIGN KEY (booker_id) REFERENCES users (id) ON DELETE CASCADE;
ALTER TABLE comments
    ADD FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE;
ALTER TABLE comments
    ADD FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE;
