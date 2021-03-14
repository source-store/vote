<%--
  Created by IntelliJ IDEA.
  User: Alexandr.Yakubov
  Date: 08.03.2021
  Time: 8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<section>
    <h3><a href="../../index.html">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create restaurant' : 'Edit restaurant'}</h2>
    <jsp:useBean id="restaurants" type="ru.yakubov.vote.to.RestaurantTo" scope="request"/>
    <form method="post" action="restaurants">
        <input type="hidden" name="id" value="${restaurants.id}">
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" value="${restaurants.name}" name="name" required></dd>
        </dl>
        <dl>
            <dt>Address:</dt>
            <dd><input type="text" value="${restaurants.address}" size=40 name="address" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
