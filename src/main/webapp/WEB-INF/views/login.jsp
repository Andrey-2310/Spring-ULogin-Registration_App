<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Log in with your account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>

<div class="container">

    <form method="POST" action="${contextPath}/login" class="form-signin">
        <script src="//ulogin.ru/js/ulogin.js"></script>
        <div class="row">
            <div style="margin-top:15px;" id="uLogin" class="col-lg-6 col-md-6 col-sm-6 col-xs-6" data-ulogin="display=panel;theme=classic;fields=first_name,last_name;providers=vkontakte,facebook,twitter;hidden=;redirect_uri=http%3A%2F%2Flocalhost%3A8080%2FcheckAuthorization;mobilebuttons=0;"></div>
            <div class="col-xs-1 visible-xs"></div>
            <h2 class=" col-lg-offset-1 col-lg-5 col-md-offset-1 col-md-5 col-sm-offset-1 col-sm-5 col-xs-5">Log in</h2>
        </div>


        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <form:form modelAttribute="userForm">

                <form:input type="text" path="username" class="form-control" placeholder="Username"
                            autofocus="true"></form:input>

                <form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>


            </form:form>
            <span>${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
            <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4>
        </div>

    </form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>