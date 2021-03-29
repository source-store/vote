DROP TRIGGER IF EXISTS delete_users ON USERS;;
DROP TRIGGER IF EXISTS delete_user_roles ON USER_ROLES;;
DROP TRIGGER IF EXISTS delete_restaurants ON RESTAURANTS;;
DROP TRIGGER IF EXISTS delete_menu ON MENU;;
DROP TRIGGER IF EXISTS delete_votes ON VOTES;;

DROP FUNCTION IF EXISTS after_delete_users();;
DROP FUNCTION IF EXISTS after_delete_user_roles();;
DROP FUNCTION IF EXISTS after_delete_restaurants();;
DROP FUNCTION IF EXISTS after_delete_menu();;
DROP FUNCTION IF EXISTS after_delete_votes();;


DROP TABLE IF EXISTS USER_ROLES;;
DROP VIEW IF EXISTS voteresult;;
DROP TABLE IF EXISTS VOTES;;
DROP TABLE IF EXISTS MENU;;
DROP TABLE IF EXISTS USERS;;
DROP TABLE IF EXISTS RESTAURANTS;;
DROP SEQUENCE IF EXISTS GLOBAL_SEQ;;

DROP TABLE IF EXISTS SHADOW_USERS;;
DROP TABLE IF EXISTS SHADOW_USER_ROLES;;
DROP TABLE IF EXISTS SHADOW_RESTAURANTS;;
DROP TABLE IF EXISTS SHADOW_MENU;;
DROP TABLE IF EXISTS SHADOW_VOTES;;


CREATE SEQUENCE GLOBAL_SEQ START WITH 100000;;

CREATE TABLE USERS
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN DEFAULT TRUE    NOT NULL
);;
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);;

CREATE TABLE USER_ROLES
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);;

CREATE TABLE RESTAURANTS
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name    VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);;

CREATE TABLE MENU
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    restaurant_id INTEGER NOT NULL,
    date          DATE    DEFAULT now() NOT NULL,
    decription	VARCHAR(255) NOT NULL,
    price BIGINT       NOT NULL,
    CONSTRAINT menu_unique_date_decription_restaurant_idx UNIQUE (date, decription, restaurant_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);;
CREATE INDEX menu_date_idx ON MENU (date);;

CREATE TABLE VOTES
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    date          DATE DEFAULT now() NOT NULL,
    user_id       INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);;
CREATE UNIQUE INDEX votes_unique_user_date_idx ON VOTES (user_id, date);;


CREATE OR REPLACE VIEW voteresult
 AS
select row_number() over () id, restaurant_id, date, sum(vt) voteCount, sum(mn) menuCount
from (select r.name, r.id restaurant_id, v.date, 1 vt, 0 mn
      from RESTAURANTS r
               join votes v on v.restaurant_id = r.id
      union all
      select r.name, r.id restaurant_id, m.date, 0 vt, 1 mn
      from menu m
               join RESTAURANTS r on r.id = m.restaurant_id
     ) as voteresult
group by name, restaurant_id, date
order by restaurant_id, date, voteCount, menuCount;;


CREATE TABLE SHADOW_USERS
(
    ID         INTEGER                 NOT NULL,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP               NOT NULL,
    enabled    BOOLEAN                 NOT NULL,
    DATE_EVENT TIMESTAMP DEFAULT now() NOT NULL
);;
CREATE INDEX shadow_users_id_idx ON SHADOW_USERS (id);;
CREATE INDEX shadow_users_email_idx ON SHADOW_USERS (EMAIL);;
CREATE INDEX shadow_users_date_event_idx ON SHADOW_USERS (DATE_EVENT);;

CREATE TABLE SHADOW_USER_ROLES
(
    user_id INTEGER                     NOT NULL,
    role    VARCHAR(255)                NOT NULL,
    DATE_EVENT TIMESTAMP DEFAULT now()  NOT NULL
);;
CREATE INDEX shadow_user_roles_id_idx ON SHADOW_USER_ROLES (user_id);;
CREATE INDEX shadow_user_roles_date_event_idx ON SHADOW_USER_ROLES (DATE_EVENT);;

CREATE TABLE SHADOW_RESTAURANTS
(
    id      INTEGER     NOT NULL,
    name    VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    DATE_EVENT TIMESTAMP DEFAULT now()  NOT NULL
);;
CREATE INDEX shadow_restaurants_id_idx ON SHADOW_RESTAURANTS (id);;
CREATE INDEX shadow_restaurants_date_event_idx ON SHADOW_RESTAURANTS (DATE_EVENT);;

CREATE TABLE SHADOW_MENU
(
    id            INTEGER               NOT NULL,
    restaurant_id INTEGER               NOT NULL,
    date          DATE                  NOT NULL,
    decription	  VARCHAR(255)          NOT NULL,
    price         BIGINT                NOT NULL,
    DATE_EVENT TIMESTAMP DEFAULT now()  NOT NULL
);;
CREATE INDEX shadow_menu_id_idx ON SHADOW_MENU (id);;
CREATE INDEX shadow_menu_date_event_idx ON SHADOW_MENU (DATE_EVENT);;

CREATE TABLE SHADOW_VOTES
(
    id              INTEGER                 NOT NULL,
    date            DATE                    NOT NULL,
    user_id         INTEGER                 NOT NULL,
    restaurant_id   INTEGER                 NOT NULL,
    DATE_EVENT      TIMESTAMP DEFAULT now() NOT NULL
);;
CREATE INDEX shadow_votes_id_idx ON SHADOW_VOTES (id);;
CREATE INDEX shadow_votes_date_event_idx ON SHADOW_VOTES (DATE_EVENT);;


CREATE OR REPLACE FUNCTION after_delete_users()
    RETURNS trigger AS
$$
BEGIN
    INSERT INTO SHADOW_USERS (ID, NAME, EMAIL, PASSWORD, REGISTERED, ENABLED) VALUES (OLD.ID, OLD.NAME, OLD.EMAIL, OLD.PASSWORD, OLD.REGISTERED, OLD.ENABLED);
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;;

CREATE OR REPLACE FUNCTION after_delete_user_roles()
    RETURNS trigger AS
$$
BEGIN
    INSERT INTO SHADOW_USER_ROLES (ROLE, USER_ID) VALUES (OLD.ROLE, OLD.USER_ID);
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;;

CREATE OR REPLACE FUNCTION after_delete_restaurants()
    RETURNS trigger AS
$$
BEGIN
    INSERT INTO SHADOW_RESTAURANTS (ID, NAME, ADDRESS) VALUES (OLD.ID, OLD.NAME, OLD.ADDRESS);
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;;

CREATE OR REPLACE FUNCTION after_delete_menu()
    RETURNS trigger AS
$$
BEGIN
    INSERT INTO SHADOW_MENU (ID, RESTAURANT_ID, DECRIPTION, PRICE, DATE) VALUES (OLD.ID, OLD.RESTAURANT_ID, OLD.DECRIPTION, OLD.PRICE, OLD.DATE);
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;;

CREATE OR REPLACE FUNCTION after_delete_votes()
    RETURNS trigger AS
    $$
    BEGIN
        INSERT INTO SHADOW_VOTES (ID, DATE, USER_ID, RESTAURANT_ID) VALUES (OLD.ID, OLD.DATE, OLD.USER_ID, OLD.RESTAURANT_ID);
        RETURN NEW;
    END;
    $$
    LANGUAGE plpgsql;;

create trigger delete_users
    after DELETE OR UPDATE on USERS FOR EACH ROW
EXECUTE PROCEDURE after_delete_users();;

create trigger delete_user_roles
    after DELETE OR UPDATE on USER_ROLES FOR EACH ROW
EXECUTE PROCEDURE after_delete_user_roles();;

create trigger delete_restaurants
    after DELETE OR UPDATE on RESTAURANTS FOR EACH ROW
EXECUTE PROCEDURE after_delete_restaurants();;

create trigger delete_menu
    after DELETE OR UPDATE on MENU FOR EACH ROW
EXECUTE PROCEDURE after_delete_menu();;

create trigger delete_votes
    after DELETE OR UPDATE on VOTES FOR EACH ROW
EXECUTE PROCEDURE after_delete_votes();;