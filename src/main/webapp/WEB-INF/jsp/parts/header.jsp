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
        <a href="${pageContext.request.contextPath}">Home</a> |
        <a href="films?action=add" target="_blank"><fmt:message key="app.addFilm"/></a> |
        <a href="index.html">_</a>
    </div>

    <div align="left" id="search">
        <form action="./" method="get">
            <input type="text" name="title" id="serchText" placeholder="<fmt:message key="app.searchPlaceHolder"/>">
            <button type="submit"><fmt:message key="app.searchButton"/></button>
        </form>
    </div>

</div>