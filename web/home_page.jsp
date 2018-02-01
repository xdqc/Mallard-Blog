<%--
  Created by IntelliJ IDEA.
  User: yyl15
  Date: 23/01/2018
  Time: 4:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%@ include file="WEB-INF/_head.jsp" %>
<script type="text/javascript" src="javascript/moment-with-locales.js"></script>
<script type="text/javascript" src="javascript/personal_blog.js"></script>
<title>Mallard Blog</title>
<style>
    body{
        background-image: url("/pictures/background.png");
    }
</style>
<body>
<%@ include file="WEB-INF/_home_page_menu.jsp" %>
<div class="container">
    <div class="row">
        <%--Logged User information panel--%>
        <c:if test="${not empty sessionScope.get('loggedInUser')}">
            <div class="col-sm-12 col-md-3 col-lg-3">
                <%@ include file="WEB-INF/_home_page_profile.jsp" %>
            </div>
            <%--list of blogs--%>
            <div class="col-sm-12 col-md-9 col-lg-9">
                <%@ include file="WEB-INF/_personal_blog_articles.jsp" %>
            </div>
            <div class="col-sm-12">
                <%@ include file="WEB-INF/_foot.jsp" %>
            </div>
        </c:if>

        <c:if test="${empty sessionScope.get('loggedInUser')}">
            <%--list of blogs--%>
            <div class="col-sm-12 col-md-1 col-lg-1">
            </div>
            <div class="col-sm-12 col-md-10 col-lg-10">
                <%@ include file="WEB-INF/_personal_blog_articles.jsp" %>
            </div>
            <div class="col-sm-12 col-md-1 col-lg-1">
            </div>
            <div class="col-sm-12">
                <%@ include file="WEB-INF/_foot.jsp" %>
            </div>
        </c:if>



</div>
</div>
</body>
</html>
