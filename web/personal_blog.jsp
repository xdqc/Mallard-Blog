<%--
  Created by IntelliJ IDEA.
  User: qd16
  Date: 1/23/2018
  Time: 6:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<%@include file="WEB-INF/_head.jsp"%>
    <title>${current_username}'s Blog</title>
</head>
<body>
<%@include file="WEB-INF/_home_page_logo.jsp"%>
<%@include file="WEB-INF/_home_page_menu.jsp"%>

<div class="container">
    <div class="row">
        <%--PersonalBlog information panel--%>
        <div class="col-sm-12 col-md-3 col-lg-3">
            <%@include file="personal_blog_profile.jsp"%>
        </div>

        <%--list of blogs--%>
        <div class="col-sm-12 col-md-9 col-lg-9">
            <%@ include file="personal_blog_articles.jsp" %>
        </div>
    </div>

</div>
</body>
</html>
