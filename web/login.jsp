<%--
  Created by IntelliJ IDEA.
  User: yh298
  Date: 24/01/2018
  Time: 10:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<form id="login-form" method="post" action="">
    <fieldset>
        <legend>Login</legend>
        <ul>
            <li>
                <label for="input1">Username</label>
                <br>
                <input id="input1" class="jfk-textinput" name="input1" type="text">
            </li>
            <br>
            <li>
                <label for="input2">Password</label>
                <br>
                <input id="input2" class="jfk-textinput" name="input2" type="password">
            </li>
            <br>
        <li>
            <div class="g-recaptcha" data-sitekey="6LejNkIUAAAAACjc9YfnAHQm8SHTWp3kaEGrXWcX"></div>
        </li>
        <li>
            <button type="submit">Login</button>
            <button>Sign In</button>
        </li>
        </ul>
    </fieldset>
</form>
</div>
</body>
</html>
