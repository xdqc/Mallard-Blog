<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<%@ include file="head.jsp"%>
    <title>You got an error</title>
</head>
<body>
<%@ include file="home_page_logo.jsp" %>
<%@ include file="home_page_menu.jsp" %>
<div class="jumbotron jumbotron-sm">
    <div class="container">
        <div class="row">
            <div class="col-sm-12 col-lg-12">
                <h1>Oops!</h1>
                <h2>404 Not Found</h2>
                <br>
                <div class="error-details">
                    ${errorMsg}
                </div>
                <br>
                <div>
                    <a href="home_page.jsp" class="btn btn-primary btn-lg">
                        <span class="fa fa-home"></span> Take Me Home </a>
                    <a href="contact.jsp" class="btn btn-default btn-lg">
                        <span class="fa fa-envelope"></span> Contact Support </a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
