<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<jsp:include page="parts/header.jsp"/>
<div class="wrapper">

    <table align="center" class="content">
        <c:forEach items="${filmsList}" var="film">
            <jsp:useBean id="film" class="filmservice.model.RatedFilm"/>
            <tr>
                <td>
                    <img class="filmImg" src="${film.image}">
                    <sec:authorize access="isAuthenticated()">
                        <p>Ваша оценка - <span yourAssessment="${film.id}">${userRatingMap[film.id].rating}</span></p>
                        <form class="assessment" action="ajax/save/rating" method="post" filmId="${film.id}">
                            <input name="filmId" type="hidden" value="${film.id}">
                            <select name="rating">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                            </select>
                            <input type="submit" value="vote">
                        </form>
                    </sec:authorize>
                </td>
                <td valign="top">
                    <h3><a href="./film?id=${film.id}" target="_blank">${film.title}</a></h3>
                    <h6 class="genre">Gencre: ${film.genre}</h6>
                    <h6 class="genre">Date: ${fn:formatDateTime(film.date)}</h6>
                    <div class="description">${film.description}</div>
                </td>
                <td>
                    <h2 filmId="${film.id}">
                            ${film.avgRating==-1?"-":film.avgRating}
                    </h2>
                </td>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <td valign="top"><a class="deleteFilm" href="./ajax/delete?id=${film.id}">x</a></td>
                </sec:authorize>
            </tr>
        </c:forEach>
    </table>
    ${paginationBlock}
</div>

<jsp:include page="parts/footer.jsp"/>