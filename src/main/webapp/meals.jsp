<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        TABLE {
            width: 100%; /* Ширина таблицы */
            border: 2px solid black; /* Рамка вокруг таблицы */
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
        }

        TD, TH {
            padding: 3px; /* Поля вокруг содержимого ячеек */
            border: 2px solid black; /* Параметры рамки */
        }

        TH {
            text-align: center; /* Выравнивание по левому краю */
            background: white; /* Цвет фона */
            color: black; /* Цвет текста */
        }
    </style>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="">Add meal</a></p>
<table>
    <thead>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    <th>&nbsp;</th>
    <th>&nbsp;</th>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr style="color:${meal.excess ? 'green' : 'red'}">
            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ parsedDateTime }"/></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="">Update</a></td>
            <td><a href="">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>