[![Codacy Badge](https://app.codacy.com/project/badge/Grade/a3cd8cf6e40d4172b041d493bede1ae3)](https://www.codacy.com/gh/source-store/topjava/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=source-store/topjava&amp;utm_campaign=Badge_Grade)

[![Build Status](https://api.travis-ci.com/source-store/topjava.svg?branch=HW05)](https://travis-ci.com/source-store/topjava)



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
body (json): UserVoteTo


REST API
--------

**AdminVoteRestController (Role ADMIN)**

|                         |*Method*  | *URL*                                                       | *Body(JSON)*  |*Code response*|  *Body(JSON)*    |  *Access*  |
|:------------------------|:--------:|-------------------------------------------------------------|:-------------:|:-------------:|:----------------:|:----------:|
|result vote current date | GET      | /rest/admin/result                                          |               | 200           | List(VoteResult) |   ADMIN    |
|result vote by period    | GET      | /rest/admin/result/in?date1={date1}&date2={date2}           |               | 200           | List(VoteResult) |   ADMIN    |
|all user profiles        | GET      | /rest/admin/profile                                         |               | 200           | List(UserVote)   |   ADMIN    |
|user profile by id       | GET      | /rest/admin/profile/{userId}                                |               | 200           | UserVote         |   ADMIN    |
|create new user          | POST     | /rest/admin/profile                                         | UserVote      | 201           | UserVote         |   ADMIN    |
|delete user              | DELETE   | /rest/admin/profile/{userId}                                |               | 204           |                  |   ADMIN    |
|update user              | PUT      | /rest/admin/profile/{userId}                                |               | 204           |                  |   ADMIN    |
|profile by email         | GET      | /rest/profiles/in?email={email}                             |               | 200           | UserVote         |   ADMIN    |
|user vote by date        | GET      | /rest/profiles/{userId}}/vote/in?date1={date1}&date2={date2}|               | 200           | List(Votes)      |   ADMIN    |


**ProfileVoteRestController (Role USER)**

|                         |*Method*  | *URL*                                       | *Body(JSON)*  |*Code response*|  *Body(JSON)*    |  *Access*  |
|:------------------------|:--------:|---------------------------------------------|:-------------:|:-------------:|:----------------:|:----------:|
|result vote current date | GET      | /rest/result                                |               | 200           | List(VoteResult) |  USER      |
|result vote by period    | GET      | /rest/result/in?date1={date1}&date2={date2} |               | 200           | List(VoteResult) |  USER      |
|current user profile     | GET      | /rest/profile                               |               | 200           | UserVote         |  USER      |
|update current user      | PUT      | /rest/profile                               | UserVoteTo    | 204           |                  |  USER      |
|vote                     | POST     | /rest/profile/{restaurantId}                |               | 201           | VoteTo           |  USER      |
|register new user        | POST     | /rest/profile/register                      | UserVoteTo    | 200           | UserVoteTo       |            |
|delete current user vote | DELETE   | /rest//profile                              |               | 204           |                  |  USER      |


**AdminRestaurantRestController (Role ADMIN)**

|                         |*Method*  | *URL*                          |    *Body(JSON)*    |*Code response*|    *Body(JSON)*     |  *Access*  |
|:------------------------|:--------:|--------------------------------|:------------------:|:-------------:|:-------------------:|:----------:|
|all restaurants          | GET      | /rest/admin/restaurant         |                    | 200           | List(Restaurants)   |  ADMIN     |
|all restaurantsTo        | GET      | /rest/admin/restaurant/to      |                    | 200           | List(RestaurantsTo) |  ADMIN     |
|restaurant               | GET      | /rest/admin/restaurant/{id}    |                    | 200           | Restaurants         |  ADMIN     |
|restaurantTo             | GET      | /rest/admin/restaurant/to/{id} |                    | 200           | RestaurantsTo       |  ADMIN     |
|create restaurant        | POST     | /rest/admin/restaurant         | Restaurants        | 201           | Restaurants         |  ADMIN     |
|delete restaurant        | DELETE   | /rest/admin/restaurant/{id}    |                    | 204           |                     |  ADMIN     |
|update restaurant        | PUT      | /rest/admin/restaurant         | Restaurants        | 204           |                     |  ADMIN     |


**RestaurantRestController (Role USER)**

|                      |*Method*  | *URL*                    |    *Body(JSON)*    |*Code response*|    *Body(JSON)*     |  *Access* |
|:---------------------|:--------:|--------------------------|:------------------:|:-------------:|:-------------------:|:---------:|
|all restaurants       | GET      | /rest/restaurant         |                    | 200           | List(Restaurants)   |  USER     |
|all restaurantsTo     | GET      | /rest/restaurant/to      |                    | 200           | List(RestaurantsTo) |  USER     |
|restaurant            | GET      | /rest/restaurant/{id}    |                    | 200           | Restaurants         |  USER     |
|restaurantTo          | GET      | /rest/restaurant/to/{id} |                    | 200           | RestaurantsTo       |  USER     |


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

