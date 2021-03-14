<%--
  Created by IntelliJ IDEA.
  User: Alexandr.Yakubov
  Date: 07.03.2021
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <h3><a href="../../index.html">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create menu' : 'Edit menu'}</h2>
    <jsp:useBean id="menu" type="ru.yakubov.vote.to.MenuTo" scope="request"/>
    <form method="post" action="menu">
        <input type="hidden" name="menuid" value="${menu.id}">
        <input type="hidden" name="id" value="${id}">
        <dl>
            <dt>Date:</dt>
            <dd><input type="date" value="${menu.date}" name="date" required></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" value="${menu.decription}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt>Price:</dt>
            <dd><input type="number" value="${menu.price}" name="price" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
