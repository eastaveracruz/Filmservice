<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User</title>
</head>
<body>
<c:forEach items="${users}" var="user">
    <jsp:useBean id="user" scope="page" class="filmservice.model.User"/>
    <p>${user.toString()}</p>
</c:forEach>
</body>
</html>
