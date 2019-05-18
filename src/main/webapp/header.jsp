<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages.app"/>

<html>
<head>
    <title><fmt:message key="app.title"/></title>
    <link rel="stylesheet" href="./resources/css/films.css">
</head>
<body style="position: relative; background-image: url(./resources/108.png);">
<div id="header" style=" width:60%; margin-left: 20%; background-color: white; padding: 30px; margin-bottom: 30px">
    <div id="menu">
        <a href="films?action=add" style="color: indianred">ADD NEW FILMS</a> |
        <a href="index.html" style="color: indianred">BACK TO MAIN</a>
    </div>

    <div align="left" id="search">
        <form action="films" method="get">
            <label for="serchText" style="font-family: Arial">Search by Title</label>
            <input type="text" name="search" id="serchText">
            <input type="hidden" name="action" value="search">
            <button type="submit">Search</button>
        </form>
    </div>

</div>