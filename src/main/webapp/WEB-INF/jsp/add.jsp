<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="parts/header.jsp"></jsp:include>
<div class="wrapper">
    <h2 style="font-size: 20px">Add Film</h2>

    <%--<c:url var="addAction" value="/film/add"/>--%>
    <%--<form:form action="/film/add" modelAttribute="film">--%>
    <%--<table>--%>
    <%--<c:if test="${!empty film.title}">--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<form:label path="id">--%>
    <%--<spring:message text="ID"/>--%>
    <%--</form:label>--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<form:input path="id" readonly="true" disabled="true"/>--%>
    <%--<form:hidden path="id"/>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--</c:if>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<form:label path="title">--%>
    <%--<spring:message text="Title"/>--%>
    <%--</form:label>--%>
    <%--</td>--%>
    <%--<td><form:input path="title"/></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<form:label path="description">--%>
    <%--<spring:message text="Description"/>--%>
    <%--</form:label>--%>
    <%--</td>--%>
    <%--<td><form:input path="description"/></td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<form:label path="genre">--%>
    <%--<spring:message text="Genre"/>--%>
    <%--</form:label>--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<form:select path="genre">--%>
    <%--<form:option value="DRAMA">DRAMA</form:option>--%>
    <%--<form:option value="COMEDY">COMEDY</form:option>--%>
    <%--<form:option value="HISTORY">HISTORY</form:option>--%>
    <%--<form:option value="WAR">WAR</form:option>--%>
    <%--<form:option value="DOCUMENTAL">DOCUMENTAL</form:option>--%>
    <%--</form:select>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td colspan="2">--%>
    <%--<input type="submit" value="<spring:message text="Save"/>"/>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--</table>--%>
    <%--</form:form>--%>

    <%--<form method="POST" action=/film/add" enctype="multipart/form-data">--%>
    <%--File to upload: <input type="file" name="file"><br/>--%>

    <%--<input type="submit" value="Upload">--%>
    <%--Press here to upload the file!--%>
    <%--</form>--%>

    <form action="./film/add" method="POST" enctype="multipart/form-data">
        <table>
            <tr>
                <td><label for="title">Title</label></td>
                <td><input type="text" name="title" id="title" required></td>
            </tr>
            <tr>
                <td><label for="description">Description</label></td>
                <td><input type="text" name="description" id="description"></td>
            </tr>
            <tr>
                <td><label for="genre">Genre</label></td>
                <td>
                    <select id="genre" name="genre">
                        <option>DRAMA</option>
                        <option>COMEDY</option>
                        <option>HISTORY</option>
                        <option>WAR</option>
                        <option>DOCUMENTAL</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="file">Choos image</label></td>
                <td><input type="file" name="file" id="file"></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="submit">Save</button>
                </td>
            </tr>
        </table>
        <br>
    </form>

</div>
<jsp:include page="parts/footer.jsp"></jsp:include>