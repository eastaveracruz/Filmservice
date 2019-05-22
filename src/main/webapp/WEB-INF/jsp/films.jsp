<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="parts/header.jsp"></jsp:include>

<div id="wrapper" style="width:60%; margin-left: 20%; background-color: white; padding: 30px">
    <table align="center">
        <c:forEach items="${filmsList}" var="film">
            <jsp:useBean id="film" scope="page" class="filmservice.model.Film"/>
            <tr>
                <td><img src="${film.image}" style="display:inline-block; width: 250px"></td>
                <td valign="top">
                    <h3 style="text-align: center; font-family: Arial">${film.title}</h3>
                    <h6 style=" padding-left: 20px; ">Gencre: ${film.genre}</h6>
                    <div style="width: 500px; padding-left: 20px; padding-bottom: 40px; font-family: Arial">${film.description}</div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<jsp:include page="parts/footer.jsp"></jsp:include>