DROP TABLE IF EXISTS USER_ROLES;
DROP VIEW IF EXISTS VOTE;
DROP TABLE IF EXISTS VOTES;
DROP TABLE IF EXISTS MENU;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS RESTAURANTS;
DROP SEQUENCE IF EXISTS GLOBAL_SEQ;


CREATE SEQUENCE GLOBAL_SEQ
    AS INTEGER
    START WITH 100000;

CREATE TABLE USERS
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN DEFAULT TRUE    NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE USER_ROLES
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE RESTAURANTS
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name    VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE MENU
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaurant_id INTEGER NOT NULL,
    date          DATE    DEFAULT now() NOT NULL,
    decription	VARCHAR(255) NOT NULL,
    price BIGINT       NOT NULL,
    CONSTRAINT menu_unique_date_decription_restaurant_idx UNIQUE (date, decription, restaurant_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);
CREATE INDEX menu_date_idx ON MENU (date);

CREATE TABLE VOTES
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    date          DATE DEFAULT now() NOT NULL,
    user_id       INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
CREATE INDEX votes_unique_user_date_idx ON VOTES (user_id, date);


CREATE OR REPLACE VIEW vote
 AS
select md.restaurant_id, md.date, count(1) count, sum(md.price) totalprice, count(v.user_id) totalvoted
from MENU md
join RESTAURANTS r on r.id = md.restaurant_id
left outer join votes v on v.restaurant_id = md.restaurant_id and v.date = md.date
group by r.name, md.restaurant_id, md.date order by md.date, r.name;
