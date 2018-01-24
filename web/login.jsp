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
    <title>Login</title>
    <style>
        ul{
            list-style: none;
        }
        form{
            position: absolute;
            margin: 18% 44% auto 39%;
        }
    </style>
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <c:if test="${not empty param.failed}">
                <div>
                    <h3>Username or password doesn't match.</h3>
                </div>
            </c:if>
            <form id="login-form" method="post" action="login">
                <fieldset>
                    <legend>Login</legend>
                    <ul>
                        <li>
                            <label for="username">Username</label>
                            <br>
                            <input id="username" class="jfk-textinput" name="username" type="text" required>
                        </li>
                        <br>
                        <li>
                            <label for="password">Password</label>
                            <br>
                            <input id="password" class="jfk-textinput" name="password" type="password" required>
                        </li>
                        <br>
                        <li>
                            <div class="g-recaptcha" data-sitekey="6LejNkIUAAAAACjc9YfnAHQm8SHTWp3kaEGrXWcX"></div>
                        </li>
                        <li>
                            <button id="login_btn" type="submit">Login</button>
                            <button id="signUp_btn">Sign Up</button>
                        </li>
                    </ul>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>
