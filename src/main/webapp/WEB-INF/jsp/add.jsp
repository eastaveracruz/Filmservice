<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="parts/header.jsp"/>
<div class="wrapper">
    <h2 style="font-size: 20px">${title}</h2>
    ${message}
    <form:form method="POST" modelAttribute="film" enctype="multipart/form-data" action="${action}" id="addFilm">
        <table>
            <c:if test="${!empty film.title}">
                <tr>
                    <td><label for="id">Id</label></td>
                    <td><form:input type="text" path="id" readonly="true" id="id"/></td>
                </tr>
            </c:if>
            <tr>
                <td><label for="title">Title</label></td>
                <td><form:input type="text" path="title"/></td>

            </tr>
            <tr>
                <td><label for="description">Description</label></td>
                <td><form:textarea type="text" path="description" cols="70" rows="15"/></td>
            </tr>
            <tr>
                <td><label for="genre">Genre</label></td>
                <td>
                    <form:select path="genre">
                        <c:forEach items="${genre}" var="g">
                            <form:option value="${g}">${g}</form:option>
                        </c:forEach>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td><label for="date">Date</label></td>
                <td>
                    <input type="date" id="date" name="rawDate" value="${film.rawDate}"/>
                </td>
            </tr>
            <tr>
                <td><label for="file">Choos image</label></td>
                <td>
                    <input type="file" name="file" id="file">
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="submit">Save</button>
                </td>
            </tr>
        </table>
    </form:form>
</div>
<jsp:include page="parts/footer.jsp"/>