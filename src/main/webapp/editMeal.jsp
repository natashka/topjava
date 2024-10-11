<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Update meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Update Meal</h2>
<c:set var="option" value="update"/>
<%@ include file="addEditMeal.jsp" %>
</body>
</html>