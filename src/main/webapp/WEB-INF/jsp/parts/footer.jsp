<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div id="header" style=" width:60%; margin-left: 20%; background-color: white; padding: 30px; margin-top: 30px">
    <c:forEach items="${userList}" var="user">
        <jsp:useBean id="user" scope="page" class="filmservice.model.User"/>
        <p>${user.toString()}</p>
    </c:forEach>
</div>

</body>
</html>