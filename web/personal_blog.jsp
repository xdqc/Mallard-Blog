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
    <%@include file="WEB-INF/_head.jsp" %>
    <title>${requestScope.get("browsingUser").getUserName()}'s Blog</title>
</head>
<style>
    body{
        background-image: url("/pictures/background.png");
    }
</style>
<body>
<%@include file="WEB-INF/_home_page_menu.jsp" %>

<div class="container">
    <div class="row">
        <%--PersonalBlog information panel--%>
        <div class="col-sm-12 col-md-3 col-lg-3">
            <%@include file="WEB-INF/_personal_blog_profile.jsp" %>
        </div>

        <%--list of blogs--%>
        <div class="col-sm-12 col-md-9 col-lg-9">
            <c:if test="${blogs.size()==0}">
                <c:if test="${!sessionScope.get('loggedInUser').equals(requestScope.get('browsingUser'))}">
                    <div class="jumbotron">
                        <h3>This user has no articles</h3>
                        <p><a class="btn btn-primary btn-lg" href="home-page" role="button">Back to Home</a></p>
                    </div>
                </c:if>
            </c:if>

            <%--logged user create new article in his own page--%>
            <c:if test="${sessionScope.get('loggedInUser').equals(requestScope.get('browsingUser'))}">
                <div class="article-panel" id="article-panel-0">
                    <div class="edit-article-area" id="edit-article-area-0">
                        <%@include file="WEB-INF/_personal_blog_create.jsp"%>
                    </div>
                </div>
            </c:if>

            <c:if test="${blogs.size()!=0}">
                <%@ include file="WEB-INF/_personal_blog_articles.jsp" %>
            </c:if>


        </div>
    </div>

</div>
</body>
</html>
