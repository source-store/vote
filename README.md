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
can create / edit users, menu items, restaurants, can view voting results


*user:*
----
can view / change himself profile, view menus and restaurants, perform voting, delete his vote in accordance with the task, can view voting results

*anonymous*
----------
registration of a new user, with USER rights 
POST /rest/profile/register
body (json): UserTo


REST API
--------

**AdminVoteRestController (Role ADMIN)**

|                         |*Method*  | *URL*                                                       | *Body(JSON)*  |*Code response*|  *Body(JSON)*    |  *Access*  |
|:------------------------|:--------:|-------------------------------------------------------------|:-------------:|:-------------:|:----------------:|:----------:|
|result vote current date | GET      | /rest/admin/results                                         |               | 200           | List(VoteResult) |   ADMIN    |
|result vote by period    | GET      | /rest/admin/results/in?date1={date1}&date2={date2}          |               | 200           | List(VoteResult) |   ADMIN    |
|all user profiles        | GET      | /rest/admin/profiles                                        |               | 200           | List(UserVote)   |   ADMIN    |
|user profile by id       | GET      | /rest/admin/profiles/{userId}                               |               | 200           | UserVote         |   ADMIN    |
|create new user          | POST     | /rest/admin/profiles                                        | UserVote      | 201           | UserVote         |   ADMIN    |
|delete user              | DELETE   | /rest/admin/profiles/{userId}                               |               | 204           |                  |   ADMIN    |
|update user              | PUT      | /rest/admin/profiles/{userId}                               |               | 204           |                  |   ADMIN    |
|profile by email         | GET      | /rest/profiles/in?email={email}                             |               | 200           | UserVote         |   ADMIN    |
|user vote by date        | GET      | /rest/profiles/{userId}}/vote/in?date1={date1}&date2={date2}|               | 200           | List(Votes)      |   ADMIN    |


**ProfileVoteRestController (Role USER)**

|                         |*Method*  | *URL*                                       | *Body(JSON)*  |*Code response*|  *Body(JSON)*    |  *Access*  |
|:------------------------|:--------:|---------------------------------------------|:-------------:|:-------------:|:----------------:|:----------:|
|result vote current date | GET      | /rest/results                               |               | 200           | List(VoteResult) |  USER      |
|result vote by period    | GET      | /rest/results/in?date1={date1}&date2={date2}|               | 200           | List(VoteResult) |  USER      |
|current user profile     | GET      | /rest/profiles                              |               | 200           | UserVote         |  USER      |
|update current user      | PUT      | /rest/profiles                              | UserTo    | 204           |                  |  USER      |
|vote                     | POST     | /rest/profiles/{restaurantId}               |               | 201           | VoteTo           |  USER      |
|register new user        | POST     | /rest/profiles/register                     | UserTo    | 200           | UserVoteTo       |            |
|delete current user vote | DELETE   | /rest//profiles                             |               | 204           |                  |  USER      |


**AdminRestaurantRestController (Role ADMIN)**

|                         |*Method*  | *URL*                          |    *Body(JSON)*    |*Code response*|    *Body(JSON)*     |  *Access*  |
|:------------------------|:--------:|--------------------------------|:------------------:|:-------------:|:-------------------:|:----------:|
|all restaurants          | GET      | /rest/admin/restaurants         |                    | 200           | List(Restaurants)   |  ADMIN     |
|all restaurantsTo        | GET      | /rest/admin/restaurants/to      |                    | 200           | List(RestaurantsTo) |  ADMIN     |
|restaurant               | GET      | /rest/admin/restaurants/{id}    |                    | 200           | Restaurants         |  ADMIN     |
|restaurantTo             | GET      | /rest/admin/restaurants/to/{id} |                    | 200           | RestaurantsTo       |  ADMIN     |
|create restaurant        | POST     | /rest/admin/restaurants         | Restaurants        | 201           | Restaurants         |  ADMIN     |
|delete restaurant        | DELETE   | /rest/admin/restaurants/{id}    |                    | 204           |                     |  ADMIN     |
|update restaurant        | PUT      | /rest/admin/restaurants         | Restaurants        | 204           |                     |  ADMIN     |


**RestaurantRestController (Role USER)**

|                      |*Method*  | *URL*                    |    *Body(JSON)*    |*Code response*|    *Body(JSON)*     |  *Access* |
|:---------------------|:--------:|--------------------------|:------------------:|:-------------:|:-------------------:|:---------:|
|all restaurants       | GET      | /rest/restaurants         |                    | 200           | List(Restaurants)   |  USER     |
|all restaurantsTo     | GET      | /rest/restaurants/to      |                    | 200           | List(RestaurantsTo) |  USER     |
|restaurant            | GET      | /rest/restaurants/{id}    |                    | 200           | Restaurants         |  USER     |
|restaurantTo          | GET      | /rest/restaurants/to/{id} |                    | 200           | RestaurantsTo       |  USER     |


**AdminMenuRestController (Role ADMIN)**

|                                        |*Method*  | *URL*                                                |    *Body(JSON)*    |*Code response*| *Body(JSON)* |  *Access* |
|:---------------------------------------|:--------:|------------------------------------------------------|:------------------:|:-------------:|:------------:|:---------:|
|menu for all restaurants for the period | GET      | /rest/admin/menu/all/in?date1={date1}&date2={date2}  |                    | 200           | List(Menu)   |  ADMIN    |
|all menu items of restaurant from date  | GET      | /rest/admin/menu/{id}/in?date1={date1}&date2={date2} |                    | 200           | List(Menu)   |  ADMIN    |
|all menu items of restaurant            | GET      | /rest/admin/menu/{id}                                |                    | 200           | List(Menu)   |  ADMIN    |
|menu item                               | GET      | /rest/admin/menu/one/{id}                            |                    | 200           | Menu         |  ADMIN    |
|create menu item                        | POST     | /rest/admin/menu/                                    | Menu               | 201           | Menu         |  ADMIN    |
|delete menu item                        | DELETE   | /rest/admin/menu/{id}                                |                    | 204           |              |  ADMIN    |
|update menu item                        | PUT      | /rest/admin/menu/{id}                                | Menu               | 205           |              |  ADMIN    |


**MenuRestController (Role USER)**

|                                        |*Method*  | *URL*                                          |    *Body(JSON)*    |*Code response*| *Body(JSON)* | *Access* |
|:---------------------------------------|:--------:|------------------------------------------------|:------------------:|:-------------:|:------------:|:--------:|
|menu for all restaurants for the period | GET      | /rest/menu/all/in?date1={date1}&date2={date2}  |                    | 200           | List<Menu>   |  USER    |
|all menu items of restaurant from date  | GET      | /rest/menu/{id}/in?date1={date1}&date2={date2} |                    | 200           | List<Menu>   |  USER    |
|all menu items of restaurant            | GET      | /rest/menu/{id}                                |                    | 200           | List<Menu>   |  USER    |


### curl samples
get all users \
`curl -u admin2@yandex.ru:password2 http://localhost:8080/vote/rest/admin/profiles`

get user (user id = 50003)\
`curl -u admin2@yandex.ru:password2 http://localhost:8080/vote/rest/admin/profiles/50003`

get result vote current date\
`curl -u admin2@yandex.ru:password2 http://localhost:8080/vote/rest/admin/results`

get result vote by period\
`curl -u admin2@yandex.ru:password2 "http://localhost:8080/vote/rest/admin/results/in?date1=2021-03-08&date2=2021-03-10"`

create new user from UserVote\
`curl -s -X POST -d "{ \"name\": \"Us4er22\", \"email\": \"user22@andex.ru\", \"password\": \"password\", \"enabled\": \"true\", \"roles\": [\"USER\", \"ADMIN\"]}" -H "Content-Type:application/json" "http://localhost:8080/vote/rest/admin/profiles" --user admin2@yandex.ru:password2`

delete user (user id = 50003)\
`curl -X DELETE http://localhost:8080/vote/rest/admin/profiles/50003 -u admin2@yandex.ru:password2`

update user (user id = 50003)\
`curl -X PUT -d "{ \"name\": \"User22\", \"email\": \"user22@an2dex.ru\", \"password\": \"password\", \"enabled\": \"true\", \"roles\": [\"USER\", \"ADMIN\"]}" -H "Content-Type:application/json" http://localhost:8080/vote/rest/admin/profiles/50002 -u admin2@yandex.ru:password2`

get profile by email (email=user2@yandex.ru)\
`curl -u admin2@yandex.ru:password2 "http://localhost:8080/vote/rest/admin/profiles/in?email=user2@yandex.ru"`


get user vote by date (period) (user id = 50002)\
`curl -u admin2@yandex.ru:password2 "http://localhost:8080/vote/rest/admin/profiles/50002/vote/in?date1=2021-03-08&date2=2021-03-10"`