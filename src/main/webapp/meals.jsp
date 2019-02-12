<%--
  Created by IntelliJ IDEA.
  User: PushkarevAV
  Date: 10.02.2019
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <th width="20%" align="center">Date</th>
        <th width="50%" align="center">Description</th>
        <th width="20%" align="center">Calories</th>
        <th width="10%" align="center">Excess</th>
    </tr>
    <jsp:useBean id="listMeals" scope="request" type="java.util.List"/>
    <c:forEach items="${listMeals}" var="meal">
        <tr style="color:${meal.excess ? "red" : "green"}">
            <td align="center">
                <fmt:parseDate value="${meal.date}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>
                <fmt:formatDate value="${parsedDate}" var="stdDate" type="date" pattern="dd.MM.yyyy"/>
                    ${stdDate} ${meal.time}
            </td>
            <td>${meal.description}</td>
            <td align="center">${meal.calories}</td>
            <td align="center">${meal.excess}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
