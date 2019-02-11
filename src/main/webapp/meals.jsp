<%--
  Created by IntelliJ IDEA.
  User: PushkarevAV
  Date: 10.02.2019
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<br>
<table border="1" width="800">
    <tr>
        <th width="20%">Date</th>
        <th width="50%">Description</th>
        <th width="20%">Calories</th>
        <th width="10%">Excess</th>
    </tr>
    <jsp:useBean id="listMeals" scope="request" type="java.util.List"/>
    <c:forEach items="${listMeals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr>
            <td>${meal.date} ${meal.time}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td bgcolor=${meal.excess ? "red" : "green"}>${meal.excess}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
