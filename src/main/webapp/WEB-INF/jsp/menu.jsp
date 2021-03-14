<%@ page import="java.util.List" %>
<%@ page import="ru.yakubov.vote.to.MenuTo" %><%--
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
    <h3><a href="../../index.html">Home</a></h3>
    <hr/>
    <h2>Menu</h2>
    <form method="get" action="menu">
        <input type="hidden" name="action" value="filter">
        <dl>
            <dt>From Date (inclusive):</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt>To Date (inclusive):</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <button type="submit">Filter</button>
    </form>
    <hr/>
    <a href="menu?action=create&id=<%
    out.print(request.getParameter("id"));
    %>">Add Menu</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Name</th>
            <th>Cost</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${menus}" var="menu">
            <jsp:useBean id="menu" type="ru.yakubov.vote.to.MenuTo"/>
            <tr>
                <td>${menu.date}</td>
                <td>${menu.decription}</td>
                <td>${menu.price}</td>
                <td>${menu.restaurant.id}</td>
                <td><a href="menu?action=update&id=${menu.restaurant.id}&menuid=${menu.id}">Update</a></td>
                <td><a href="menu?action=delete&id=${menu.restaurant.id}&menuid=${menu.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>

</body>
</html>
