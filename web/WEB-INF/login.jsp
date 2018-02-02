<%--
  Created by IntelliJ IDEA.
  User: yh298
  Date: 24/01/2018
  Time: 10:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@include file="_head.jsp" %>
    <title>Login</title>
    <script src="https://www.google.com/recaptcha/api.js"
            async defer></script>
</head>
<body>
<div class="container" style="height: 60%">
    <div class="row">
        <div class="col-md-3 ">
        </div>
        <div class="col-md-6 ">
            <div class="panel panel-default" id="bg" style=" box-shadow: 3px 4px 4px 4px floralwhite; border-color: lightgray; margin-top: 150px ; padding: 5em;">
                <c:if test="${not empty param.failed}">
                    <div>
                        <h4 style="color: red">Username or password doesn't match.</h4>
                    </div>
                </c:if>
                <c:if test="${not empty param.login && param.login.equals('newSignUp')}">
                    <div>
                        <h4 style="color:green;">Congratulations you have signed up ! Please Login now </h4>
                    </div>
                </c:if>


                <form id="login-form"  method="post" action="login?login=1">

                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="username">Username</label>
                            <input id="username"  class="form-control" name="username" placeholder="Enter Username" type="text" required>
                        </div>
                    </div>
                    <br>

                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="password">Password</label>
                            <input id="password" class="form-control" name="password" placeholder="Enter Password" type="password" required>
                        </div>
                    </div>
                    <br>

                    <div class="form-group">
                        <label class="control-label">reCaptcha</label>
                        <div style="width: 50%">
                            <div class="g-recaptcha" data-sitekey="6LejNkIUAAAAACjc9YfnAHQm8SHTWp3kaEGrXWcX"></div>
                        </div>
                    </div>
                    <input id="login_btn" type="submit" class="btn btn-primary" value="Login">
                </form>
                <div style="position: relative; margin: -48px 0 0 80px">
                    <form action="sign-up?signUp=0" method="post">
                        <input type="submit" id="signUp_btn" class="btn btn-default" value="Sign Up">
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-3">
        </div>
    </div>
</div>

</body>
</html>

