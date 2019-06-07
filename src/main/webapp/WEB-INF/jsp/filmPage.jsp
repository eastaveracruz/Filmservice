<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<jsp:include page="parts/header.jsp"/>

<div class="wrapper">

    <table class="filmPage">
        <tr>
            <td colspan="2"><h1>${film.title}</h1></td>
        </tr>
        <tr>
            <td colspan="2"><img src="${film.image}"></td>
        </tr>
        <tr>
            <td class="info">
                <h4>Genre: ${film.genre}</h4>
                <h4>Date: ${film.date}</h4>
            </td>
            <td class="info">
                <h2 filmId="${film.id}">
                    ${film.avgRating==-1?"-":film.avgRating}
                </h2>
            </td>
        </tr>
        <tr>
            <td colspan="2" class="select">
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
        </tr>
        <tr>
            <td colspan="2">
                <div class="description">${film.description}</div>
            </td>
        </tr>
    </table>

</div>

<jsp:include page="parts/footer.jsp"/>