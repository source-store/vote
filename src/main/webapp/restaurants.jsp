<%--
  Created by IntelliJ IDEA.
  User: Alexandr.Yakubov
  Date: 06.03.2021
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Restaurants</h2>
    <form method="get" action="restaurants">
        <input type="hidden" name="action" value="filter">
        <dl>
            <dt>From Date (inclusive):</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt>To Date (inclusive):</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt>From Time (inclusive):</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt>To Time (exclusive):</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit">Filter</button>
    </form>
    <hr/>
    <a href="restaurants?action=create">Add Restaurant</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Name</th>
            <th>Address</th>
            <th>Menu</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${restaurants}" var="restaurant">
            <jsp:useBean id="restaurant" type="ru.yakubov.vote.to.RestaurantTo"/>
            <tr>
                <td>${restaurant.name}</td>
                <td>${restaurant.address}</td>
                <td><a href="menu?action=menu&id=${restaurant.id}">Menu</a></td>
                <td><a href="restaurants?action=update&id=${restaurant.id}">Update</a></td>
                <td><a href="restaurants?action=delete&id=${restaurant.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>

</body>
</html>
