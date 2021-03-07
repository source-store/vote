DELETE FROM USER_ROLES;
DELETE FROM VOTES;
DELETE FROM MENU;
DELETE FROM USERS;
DELETE FROM RESTAURANTS;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

select * from USERS;

INSERT INTO USERS (NAME, EMAIL, PASSWORD) VALUES
('Admin1', 'admin1@yandex.ru', 'password1'),
('eater1', 'eater2@yandex.ru', 'password2'),
('eater2', 'eater3@yandex.ru', 'password3'),
('eater3', 'eater4@yandex.ru', 'password4');

INSERT INTO USER_ROLES (ROLE, USER_ID) VALUES
('ROLE_ADMIN', 100000),
('ROLE_USER', 100001),
('ROLE_USER', 100002),
('ROLE_USER', 100003);

INSERT INTO RESTAURANTS (NAME, ADDRESS) VALUES
('Chinese restaurant', 'Kutuzovsky prospect 216'),
('European restaurant', 'Letnikovskaya street 2'),
('Russian restaurant', 'the Red Square'),
('Eat at home', 'home Sweet Home');


INSERT INTO MENU (restaurant_id, decription, PRICE, DATE) VALUES
(100004, 'Jiaozi ', 500,            '2021-03-08'),
(100004, 'Wonton', 1200,            '2021-03-08'),
(100004, 'Kung Pao', 2100,          '2021-03-08'),

(100005, 'Bubble and squeak', 1100, '2021-03-08'),
(100005, 'Fish and Chips', 530,     '2021-03-08'),
(100005, 'Roast beef', 2100,        '2021-03-08'),

(100006, 'Olivier salad', 320,      '2021-03-09'),
(100006, 'Borscht', 550,            '2021-03-09'),
(100006, 'Dumplings', 700,          '2021-03-09'),

(100007, 'Vodka', 300,              '2021-03-08'),
(100007, 'Fish', 270,               '2021-03-08'),
(100007, 'Sneck', 290,              '2021-03-08'),
(100007, 'Thea', 90,                '2021-03-08');

