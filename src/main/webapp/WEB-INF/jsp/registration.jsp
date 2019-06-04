<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="parts/header.jsp"/>

<div class="wrapper">
    <form:form method="POST" modelAttribute="userForm" class="form-signin">
        <h2 class="form-signin-heading">Create your account</h2>
        <spring:bind path="login">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="text" path="login" placeholder="Username" autofocus="true"/>
                <form:errors path="login"/>
            </div>
        </spring:bind>
        <spring:bind path="password">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" placeholder="Password"/>
                <form:errors path="password"/>
            </div>
        </spring:bind>
        <spring:bind path="confirmPassword">
            <div class="${status.error ? 'has-error' : ''}">
                <form:input type="password" path="confirmPassword" placeholder="Confirm your password"/>
                <form:errors path="confirmPassword"/>
            </div>
        </spring:bind>

        <button type="submit">Submit</button>
    </form:form>
</div>
<jsp:include page="parts/footer.jsp"/>