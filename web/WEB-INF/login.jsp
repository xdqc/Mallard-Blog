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
    <style type="text/css">
        body{
            margin: 0 auto;
            background-image: url("../pictures/backs.jpg");
            background-repeat: no-repeat;
            background-size: 100% 1024px;
        }
        #bg{
            background-color: rgba(52, 73, 94, 0.7);
        }
        .login-form input{
            height: 45px;
            width: 300px;
            font-size: 18px;
            margin-left: 1em;
            margin-bottom: 20px;
            background-color: #fff;
            padding-left: 30px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-3">
        </div>
        <div class="col-md-6">
            <div id="bg" style=" margin-top: 150px ; padding: 5em;">
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


                <form id="login-form"  method="post" action="login?login=1" class="login-form">
                    <div class="row">
                        <div class="col-lg-2 col-lg-offset-1">
                            <div class="form-group">
                                <div style="width: 50%">
                                    <input id="username" class="jfk-textinput" name="username" placeholder="Enter Username" type="text" required>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>

                    <div class="row">
                        <div class="col-lg-2 col-lg-offset-1">
                            <div class="form-group">
                                <div style="width: 50%">
                                    <input id="password" class="jfk-textinput" name="password" placeholder="Enter Password" type="password" required>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>


                    <div class="row">
                        <div class="col-lg-2 col-lg-offset-1">
                            <div class="form-group" style="margin-left: 1em">
                                <label class="control-label">reCaptcha</label>
                                <div style="width: 50%">
                                    <div class="g-recaptcha" data-sitekey="6LejNkIUAAAAACjc9YfnAHQm8SHTWp3kaEGrXWcX"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" style=" margin-left: 4em; margin-top: 1em">
                        <button  style="width: 20em;" type="submit" class="btn btn-primary">Login</button>
                    </div>
                </form>


                    <form action="sign-up?signUp=0" method="post">
                        <div style="margin-left: 10em">
                                <p style="color: white"> Need a account ?</p>
                                <button type="submit" class="btn btn-default">Sign-up</button>
                            </div>
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