DELETE
FROM USER_ROLES;
DELETE
FROM VOTES;
DELETE
FROM MENUS;
DELETE
FROM USERS;
DELETE
FROM RESTAURANTS;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;


INSERT INTO USERS (ID, NAME, EMAIL, PASSWORD)
VALUES (50000, 'Admin1', 'admin1@yandex.ru', '{noop}password1'),
       (50001, 'Admin2', 'admin2@yandex.ru', '{noop}password2'),
       (50002, 'User1', 'user1@yandex.ru', '{noop}password3'),
       (50003, 'User2', 'user2@yandex.ru', '{noop}password4'),
       (50004, 'User3', 'user3@yandex.ru', '{noop}password5');

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('ADMIN', 50000),
       ('ADMIN', 50001),
       ('USER', 50002),
       ('USER', 50003),
       ('ADMIN', 50003),
       ('USER', 50004);

INSERT INTO RESTAURANTS (ID, NAME, ADDRESS)
VALUES (50005, 'Ресторан1', 'адрес1'),
       (50006, 'Ресторан2', 'адрес2'),
       (50007, 'Ресторан3', 'адрес3'),
       (50008, 'Ресторан4', 'адрес4');


INSERT INTO MENUS (id, restaurant_id, description, PRICE, DATE)
VALUES (50009, 50005, 'menu1', 50, '2021-03-10'),
       (50010, 50005, 'menu2', 10, '2021-03-10'),
       (50011, 50005, 'menu3', 20, '2021-03-10'),

       (50012, 50006, 'menu4', 30, '2021-03-10'),
       (50013, 50006, 'menu5', 20, '2021-03-10'),
       (50014, 50005, 'menu6', 25, '2021-03-11'),

       (50015, 50005, 'menu7', 15, '2021-03-11'),
       (50016, 50007, 'menu8', 10, '2021-03-15'),
       (50017, 50008, 'menu9', 15, '2021-03-17');

INSERT INTO votes (id, DATE, user_id, restaurant_id)
VALUES (50018, '2021-03-08', 50002, 50005),
       (50019, '2021-03-08', 50003, 50005),
       (50020, '2021-03-08', 50004, 50006),
       (50021, '2021-03-10', 50004, 50006);
