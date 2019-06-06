<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="parts/header.jsp"/>
<div class="wrapper">
    <h2 style="font-size: 20px">Add Film</h2>
    <form action="./film/add" method="POST" enctype="multipart/form-data">
        <table>
            <tr>
                <td><label for="title">Title</label></td>
                <td><input type="text" name="title" id="title" required></td>
            </tr>
            <tr>
                <td><label for="description">Description</label></td>
                <td><textarea name="description" id="description" cols="70" rows="15"></textarea></td>
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
<jsp:include page="parts/footer.jsp"/>