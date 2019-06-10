<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setBundle basename="messages.app"/>

<html>
<head>
    <title><fmt:message key="app.title"/></title>
    <link rel="stylesheet" href="./resources/css/films.css">
    <link rel="icon" href="./resources/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="./resources/favicon.ico" type="image/x-icon">

    <script type="text/javascript" src="webjars/jquery/3.3.1-2/jquery.js" defer></script>
    <script type="text/javascript" src="resources/js/film.js" defer></script>
</head>
<body>
<header>
    <div id="menu" class="menu">
        <a href="${pageContext.request.contextPath}"><fmt:message key="app.home"/></a> |
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="./add"><fmt:message key="app.addFilm"/></a> |
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <a href="./login"><fmt:message key="app.logIn"/></a> |
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <a href="<c:url value="/logout"/>"><fmt:message key="app.logout"/></a> |
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <a href="./registration"><fmt:message key="app.registration"/></a> |
        </sec:authorize>
        <c:if test="${user != null}">
            <span class="hello">Hello ${user}</span>
        </c:if>
    </div>
    <div align="left" id="search">
        <form action="./1" method="get">
            <input id="searchByTitle" type="text" name="title" id="serchText"
                   placeholder="<fmt:message key="app.searchPlaceHolder"/>">
            <button type="submit"><fmt:message key="app.searchButton"/></button>
        </form>
    </div>
    <div align="right" id="sort">
        <form action="./1" method="get">
            <sec:authorize access="isAuthenticated()">
            <select name="assessment">
                <option value="">-</option>
                <option value="1">оценненные</option>
                <option value="0">не оценненные</option>
            </select>
            </sec:authorize>.
            <select name="genre">
                <option value="">-</option>
                <c:forEach items="${genre}" var="g">
                    <option value="${g}">${g}</option>
                </c:forEach>
            </select>
            <select name="sort">
                <option value="title_asc">Title | ascending</option>
                <option value="title_desc">Title | descending</option>
                <option value="date_desc">Date | ascending</option>
                <option value="datee_asc">Date | descending</option>
                <option value="rating_desc">Rating | ascending</option>
                <option value="rating_asc">Rating | descending</option>
            </select>
            <button type="submit">Filter</button>
        </form>
    </div>
</header>