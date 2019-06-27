<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:setBundle basename="messages.app"/>
<c:set var="path" value="${pageContext.request.getRequestURL()}"/>

<html>
<head>
    <title><fmt:message key="app.title"/></title>
    <link rel="stylesheet" href="./resources/css/films.css">
    <link rel="icon" href="./resources/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="./resources/favicon.ico" type="image/x-icon">

    <script type="text/javascript" src="webjars/jquery/3.3.1-2/jquery.js" defer></script>
    <script type="text/javascript" src="resources/js/film.js" defer></script>
</head>
<body>${pageContext.request.getPathInfo()}
<header>
    <div id="menu" class="menu">
        <a href="./"><fmt:message key="app.home"/></a> |
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
    <c:if test="${filmsList != null}">
        <div align="right" id="sort">
            <form:form method="get" action="./1" modelAttribute="getParameters">
                <sec:authorize access="isAuthenticated()">
                    <form:select path="assessment">
                        <form:option value="">-</form:option>
                        <form:option value="1">оценненные</form:option>
                        <form:option value="0">не оценненные</form:option>
                    </form:select>
                </sec:authorize>
                <form:select path="genre">
                    <form:option value="-">-</form:option>
                    <c:forEach items="${genre}" var="g">
                        <form:option value="${g}">${g}</form:option>
                    </c:forEach>
                </form:select>
                <form:select path="sortString">
                    <form:option value="title_asc">Title | ascending</form:option>
                    <form:option value="title_desc">Title | descending</form:option>
                    <form:option value="date_desc">Date | ascending</form:option>
                    <form:option value="date_asc">Date | descending</form:option>
                    <form:option value="rating_desc">Rating | ascending</form:option>
                    <form:option value="rating_asc">Rating | descending</form:option>
                </form:select>
                <button type="submit">Filter</button>
            </form:form>
        </div>
    </c:if>
</header>