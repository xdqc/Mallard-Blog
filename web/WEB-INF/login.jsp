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
    <script src='https://www.google.com/recaptcha/api.js' async defer></script>
    <script type="text/javascript" src="http://www.google.com/recaptcha/api/js/recaptcha_ajax.js"></script>
    <style>
        .msg-error{
            color: red;
        }
        #g-recaptcha error{
            border:solid 2px #c64848;
            padding: .2em;
            width:19em;
        }
    </style>
</head>
<body>
<div class="container" style="height: 60%">
    <div class="row">
        <div class="col-md-3 ">
        </div>
        <div class="col-md-6 ">
            <div class="panel panel-default" style="box-shadow: 1px 3px 4px 2px floralwhite; border-color: lightgray; margin-top: 20em ; padding: 5em;">
                <c:if test="${not empty param.failed}">
                    <div>
                        <h4 style="color: red">Username or password doesn't match.</h4>
                    </div>
                </c:if>


                <form id="login-form" method="post" action="login?login=1">

                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="username">Username</label>
                            <input id="username" class="jfk-textinput" name="username" type="text" required>
                        </div>
                    </div>
                    <br>
                    <div class="form-group">
                        <div style="width: 50%">
                            <label for="password">Password</label>
                            <input id="password" class="jfk-textinput" name="password" type="password" required>
                        </div>
                    </div>
                    <br>

                    <div class="form-group">
                        <div style="width: 50%">
                            <span class="msg-error error" ></span>
                            <div id="recaptcha" class="g-recaptcha" data-sitekey="6LejNkIUAAAAACjc9YfnAHQm8SHTWp3kaEGrXWcX">
                            </div>
                            <button class="btn" id="btn-validate">Validate reCAPTCHA</button>
                            <script>
                                $( '#btn-validate' ).click(function(){
                                    var $captcha = $( '#recaptcha' ),
                                        response = grecaptcha.getResponse();

                                    if (response.length === 0) {
                                        $( '.msg-error').text( "reCAPTCHA is mandatory" );
                                        if( !$captcha.hasClass( "error" ) ){
                                            $captcha.addClass( "error" );
                                        }
                                    } else {
                                        $( '.msg-error' ).text('');
                                        $captcha.removeClass( "error" );
                                        alert( 'reCAPTCHA marked' );
                                    }
                                })
                            </script>
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
