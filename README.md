[![Codacy Badge](https://app.codacy.com/project/badge/Grade/a113d3682286413f9fa63be4b0e07d42)](https://www.codacy.com/gh/source-store/vote/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=source-store/vote&amp;utm_campaign=Badge_Grade)

[![Build Status](https://api.travis-ci.com/source-store/vote.svg?branch=travis_postgres)](https://travis-ci.com/source-store/vote.svg?branch=travis_postgres)


Topjava Graduation Project
==========================

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.
---------------------------------------------------------------------------------------------------

The task is:

Build a voting system for deciding where to have lunch.

- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
  - If it is before 11:00 we assume that he changed his mind.
  - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.


NOTE:
====
system for storing historical data is implemented by means of a DBMS. historical data is stored in tables prefixed with *SHADOW_*


Two roles are *administrator* (ADMIN) and *user* (USER).

*administrator:*
-------------
can create / edit users, menu items, restaurants


*user:*
----
can view / change himself profile, view menus and restaurants, perform voting (in accordance with the task), can view voting results

*anonymous*
----------
registration of a new user, with USER rights 
POST /rest/profile/register
body (json): UserTo


REST API
--------

##for example JSON
User {"name": "UserName", "email": "mail@andex.ru", "password": "userPassword", "enabled": "true", "roles": ["USER", "ADMIN"]}\
UserTo { "name": "UserName", "email": "mail@yandex.ru", "password": "userPassword"}\
Restaurant { "name": "nameRestaurant", "address": "addressRestaurant"}\
Menu { "restaurant": {"id": intId},"date": [YYYY, M, D],"description": "menuDescription", "price": intPrice }\
VoteTo {"id":intId,"date":"YYYY-MM-DD","userId":intId,"restaurantId":intId}

**AdminVoteRestController (Role ADMIN)**

|                         |*Method*  | *URL*                                                       | *Body(JSON)* |*Code response*| *Body(JSON)* |  *Access*  |
|:------------------------|:--------:|-------------------------------------------------------------|:------------:|:-------------:|:------------:|:----------:|
|all user profiles        | GET      | /rest/admin/profiles                                        |              | 200           | List(User)   |   ADMIN    |
|user profile by id       | GET      | /rest/admin/profiles/{userId}                               |              | 200           | User         |   ADMIN    |
|profile by email         | GET      | /rest/profiles/in?email={email}                             |              | 200           | User         |   ADMIN    |
|create new user          | POST     | /rest/admin/profiles                                        | User         | 201           | User         |   ADMIN    |
|delete user              | DELETE   | /rest/admin/profiles/{userId}                               |              | 204           |              |   ADMIN    |
|update user              | PUT      | /rest/admin/profiles/{userId}                               |              | 204           |              |   ADMIN    |


**ProfileVoteRestController (Role USER)**

|                           |*Method*| *URL*                                              | *Body(JSON)* |*Code response*| *Body(JSON)* |  *Access*  |
|:--------------------------|:------:|----------------------------------------------------|:------------:|:-------------:|:------------:|:----------:|
|current user profile       | GET    | /rest/profile                                      |              | 200           | User         |  USER      |
|current user vote          | GET    | /rest/profile/vote                                 |              | 200           | VoteTo       |  USER      |
|user vote by date (period) | GET    | /rest/profile/votes/in?date1={date1}&date2={date2} |              | 200           | List(VoteTo) |  USER      |
|update current user        | PUT    | /rest/profile                                      | UserTo       | 204           |              |  USER      |
|vote                       | POST   | /rest/profile/vote?id={restaurantId}               |              | 201           | VoteTo       |  USER      |
|update current vote        | PUT    | /rest/profile/vote?id={restaurantId}               |              | 201           | VoteTo       |  USER      |
|register new user          | POST   | /rest/profile/register                             | UserTo       | 200           | User         |            |



**AdminRestaurantRestController (Role ADMIN)**

|                         |*Method*| *URL*                        | *Body(JSON)* |*Code response*|    *Body(JSON)*   | *Access* |
|:------------------------|:------:|------------------------------|:------------:|:-------------:|:-----------------:|:--------:|
|all restaurants          | GET    | /rest/admin/restaurants      |              | 200           | List(Restaurants) | ADMIN    |
|restaurant               | GET    | /rest/admin/restaurants/{id} |              | 200           | Restaurants       | ADMIN    |
|create restaurant        | POST   | /rest/admin/restaurants      | Restaurants  | 201           | Restaurants       | ADMIN    |
|delete restaurant        | DELETE | /rest/admin/restaurants/{id} |              | 204           |                   | ADMIN    |
|update restaurant        | PUT    | /rest/admin/restaurants      | Restaurants  | 204           |                   | ADMIN    |


**RestaurantRestController (Role USER)**

|                      |*Method*  | *URL*                  |    *Body(JSON)*    |*Code response*|    *Body(JSON)*     | *Access* |
|:---------------------|:--------:|------------------------|:------------------:|:-------------:|:-------------------:|:--------:|
|all restaurants       | GET      | /rest/restaurants      |                    | 200           | List(Restaurants)   |  USER    |
|restaurant            | GET      | /rest/restaurants/{id} |                    | 200           | Restaurants         |  USER    |


**AdminMenuRestController (Role ADMIN)**

|                                            |*Method* | *URL*                                              | *Body(JSON)* |*Code response*| *Body(JSON)* |  *Access* |
|:-------------------------------------------|:-------:|----------------------------------------------------|:------------:|:-------------:|:------------:|:---------:|
|get menu for all restaurants for the period | GET     | /rest/admin/menus/in?date1={date1}&date2={date2}   |              | 200           | List(Menu)   |  ADMIN    |
|get all menu items of restaurant from date  | GET     | /rest/admin/menus?{id}&date1={date1}&date2={date2} |              | 200           | List(Menu)   |  ADMIN    |
|menu item                                   | GET     | /rest/admin/menus/{id}                             |              | 200           | Menu         |  ADMIN    |
|create menu item                            | POST    | /rest/admin/menus/                                 | Menu         | 201           | Menu         |  ADMIN    |
|delete menu item                            | DELETE  | /rest/admin/menus/{id}                             |              | 204           |              |  ADMIN    |
|update menu item                            | PUT     | /rest/admin/menus/{id}                             | Menu         | 204           |              |  ADMIN    |


**MenuRestController (Role USER)**

|                                        |*Method*  | *URL*                                        | *Body(JSON)* |*Code response*| *Body(JSON)* | *Access* |
|:---------------------------------------|:--------:|----------------------------------------------|:------------:|:-------------:|:------------:|:--------:|
|menu for today                          | GET      | /rest/menus/today                            |              | 200           | List<Menu>   |  USER    |
|menu item                               | GET      | /rest/menus/{id}                             |              | 200           | Menu         |  USER    |
|menu for all restaurants for the period | GET      | /rest/menus/in?date1={date1}&date2={date2}   |              | 200           | List<Menu>   |  USER    |
|all menu items of restaurant from date  | GET      | /rest/menus?{id}&date1={date1}&date2={date2} |              | 200           | List<Menu>   |  USER    |



## curl samples
### AdminVoteRestController samples

get all users \
`curl -u admin2@yandex.ru:password2 http://localhost:8080/vote/rest/admin/profiles`

get user (user id = 50003)\
`curl -u admin2@yandex.ru:password2 http://localhost:8080/vote/rest/admin/profiles/50003`

get profile by email (email=user2@yandex.ru)\
`curl -u admin2@yandex.ru:password2 "http://localhost:8080/vote/rest/admin/profiles/in?email=user2@yandex.ru"`

create new user from User\
`curl -s -X POST -d "{ \"name\": \"Us4er22\", \"email\": \"user22@andex.ru\", \"password\": \"password\", \"enabled\": \"true\", \"roles\": [\"USER\", \"ADMIN\"]}" -H "Content-Type:application/json" "http://localhost:8080/vote/rest/admin/profiles" --user admin2@yandex.ru:password2`

delete user (user id = 50003)\
`curl -X DELETE http://localhost:8080/vote/rest/admin/profiles/50003 -u admin2@yandex.ru:password2`

update user (user id = 50003)\
`curl -X PUT -d "{ \"name\": \"User22\", \"email\": \"user22@an2dex.ru\", \"password\": \"password\", \"enabled\": \"true\", \"roles\": [\"USER\", \"ADMIN\"]}" -H "Content-Type:application/json" http://localhost:8080/vote/rest/admin/profiles/50002 -u admin2@yandex.ru:password2`

### ProfileVoteRestController samples

get current user profile\
`curl -u user2@yandex.ru:password4 http://localhost:8080/vote/rest/profile`

get current user vote\
`curl -u user2@yandex.ru:password4 http://localhost:8080/vote/rest/profile/vote`

get user vote by date (period)\
`curl -u user2@yandex.ru:password4 "http://localhost:8080/vote/rest/profile/votes/in?date1=2021-03-08&date2=2021-03-10"`

update current user\
`curl -X PUT -d "{ \"name\": \"User22\", \"email\": \"user22@an2dex.ru\", \"password\": \"password4\"}" -H "Content-Type:application/json" http://localhost:8080/vote/rest/profile -u user2@yandex.ru:password4`

vote\
`curl -X POST -u user2@yandex.ru:password4 "http://localhost:8080/vote/rest/profile/vote?id=50007"`

update current vote\
`curl -X PUT -u user2@yandex.ru:password4 "http://localhost:8080/vote/rest/profile/vote?id=50007"`

register new user\
`curl -X POST -d "{ \"name\": \"User22\", \"email\": \"user22@an2dex.ru\", \"password\": \"password4\"}" -H "Content-Type:application/json"  "http://localhost:8080/vote/rest/profile/register"`

### AdminRestaurantRestController samples

get all restaurants\
`curl -u admin2@yandex.ru:password2 http://localhost:8080/vote/rest/admin/restaurants`

get restaurant\
`curl -u admin2@yandex.ru:password2 http://localhost:8080/vote/rest/admin/restaurants/50006`

create restaurant\
`curl -s -X POST -d "{ \"name\": \"NAME test\", \"address\": \"ADDRESS test\"}" -H "Content-Type:application/json" "http://localhost:8080/vote/rest/admin/restaurants" --user admin2@yandex.ru:password2`

delete restaurant\
`curl -s -X DELETE "http://localhost:8080/vote/rest/admin/restaurants/100000" --user admin2@yandex.ru:password2`

update restaurant\
`curl -s -X PUT -d "{ \"name\": \"NAME test\", \"address\": \"ADDRESS222 test\"}" -H "Content-Type:application/json" "http://localhost:8080/vote/rest/admin/restaurants/50006" --user admin2@yandex.ru:password2`

### RestaurantRestController samples

get all restaurants\
`curl -u user2@yandex.ru:password4 http://localhost:8080/vote/rest/restaurants`

get restaurant\
`curl -u user2@yandex.ru:password4 http://localhost:8080/vote/rest/restaurants/50007`

### AdminMenuRestController samples

get menu for all restaurants for the period\
`curl -u admin2@yandex.ru:password2 "http://localhost:8080/vote/rest/admin/menus/in?date1=2021-03-08&date2=2021-03-10"`

get all menu items of restaurant from date\
`curl -u admin2@yandex.ru:password2 "http://localhost:8080/vote/rest/admin/menus?id=50006&date1=2021-03-08&date2=2021-03-10"`

get menu item\
`curl -u admin2@yandex.ru:password2 "http://localhost:8080/vote/rest/admin/menus/50012"`

create menu item\
`curl -s -X POST -d "{ \"restaurant\": {\"id\": 50005},\"date\": [2021, 3, 18],\"description\": \"m446re7u11\", \"price\": 5000 }" -H "Content-Type:application/json" "http://localhost:8080/vote/rest/admin/menus/" --user admin2@yandex.ru:password2`

delete menu item\
`curl -s -X DELETE "http://localhost:8080/vote/rest/admin/menus/50012" --user admin2@yandex.ru:password2`

update menu item\
`curl -s -X PUT -d "{ \"restaurant\": {\"id\": 50005},\"date\": [2021, 3, 18],\"description\": \"m4re7u11\", \"price\": 5000 }" -H "Content-Type:application/json" "http://localhost:8080/vote/rest/admin/menus/50011" --user admin2@yandex.ru:password2`


### MenuRestController samples

get menu for today\
`curl -u user2@yandex.ru:password4 "http://localhost:8080/vote/rest/menus/today"`

get menu item\
`curl -u user2@yandex.ru:password4 "http://localhost:8080/vote/rest/menus/50014"`

get menu for all restaurants for the period\
`curl -u user2@yandex.ru:password4 "http://localhost:8080/vote/rest/menus/in?date1=2021-03-08&date2=2021-03-10"`

get menu for all restaurants for the period\
`curl -u user2@yandex.ru:password4 "http://localhost:8080/vote/rest/menus?id=50006&date1=2021-03-08&date2=2021-03-10"`
