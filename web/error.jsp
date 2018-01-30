<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<%@ include file="WEB-INF/_head.jsp"%>
    <title>You got an error</title>
</head>
<body>
<%@ include file="WEB-INF/_home_page_menu.jsp" %>
<div class="jumbotron jumbotron-sm">
    <div class="container">
        <div class="row">
            <div class="col-sm-12 col-lg-12">
                <h1>Oops!</h1>
                <h2>404 Not Found</h2>
                <br>
                <div class="error-details">
                    ${param.errorMsg}
                </div>
                <br>
                <div>
                    <a href="home-page" class="btn btn-primary btn-lg">
                        <span class="fa fa-home"></span> Take Me Home </a>
                    <a href="contact" class="btn btn-default btn-lg">
                        <span class="fa fa-envelope"></span> Contact Support </a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
