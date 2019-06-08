<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="parts/header.jsp"/>
<div class="wrapper">
    <h2 style="font-size: 20px">${title}</h2>

    <%--<form action="./film/add" method="POST" enctype="multipart/form-data">--%>
    <%--<table>--%>
    <%--<tr>--%>
    <%--<td><label for="title">Title</label></td>--%>
    <%--<td><input type="text" name="title" id="title" required></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td><label for="description">Description</label></td>--%>
    <%--<td><textarea name="description" id="description" cols="70" rows="15"></textarea></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td><label for="genre">Genre</label></td>--%>
    <%--<td>--%>
    <%--<select id="genre" name="genre">--%>
    <%--<option>DRAMA</option>--%>
    <%--<option>COMEDY</option>--%>
    <%--<option>HISTORY</option>--%>
    <%--<option>WAR</option>--%>
    <%--<option>DOCUMENTAL</option>--%>
    <%--</select>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td><label for="date">Date</label></td>--%>
    <%--<td><input type="date" name="date" id="date" /></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td><label for="file">Choos image</label></td>--%>
    <%--<td><input type="file" name="file" id="file"></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td></td>--%>
    <%--<td>--%>
    <%--<button type="submit">Save</button>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--</table>--%>
    <%--<br>--%>
    <%--</form>--%>
    <span sta>${error}</span>
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
                        <form:option value="DRAMA">DRAMA</form:option>
                        <form:option value="COMEDY">COMEDY</form:option>
                        <form:option value="HISTORY">HISTORY</form:option>
                        <form:option value="WAR">WAR</form:option>
                        <form:option value="DOCUMENTAL">DOCUMENTAL</form:option>
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
                    <input type="hidden" name="ex_file" value="${film.image}">
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