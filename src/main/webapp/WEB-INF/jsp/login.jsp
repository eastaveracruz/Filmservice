<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<jsp:include page="parts/header.jsp"></jsp:include>

<div class="wrapper">

    <form method="POST" action="${contextPath}/login" class="form-signin">
        <h2 class="form-heading">Log in</h2>

        <table>
            <tr>
                <td><span>${message}</span></td>
            </tr>
            <tr>
                <td><input name="username" type="text" class="form-control" placeholder="Username" autofocus="true"/>
                </td>
            </tr>
            <tr>
                <td><input name="password" type="password" class="form-control" placeholder="Password"/></td>
            </tr>
            <tr>
                <td><span>${error}</span></td>
            </tr>
            <tr>
                <td>
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
                </td>
            </tr>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4>
    </form>

</div>

<jsp:include page="parts/footer.jsp"></jsp:include>