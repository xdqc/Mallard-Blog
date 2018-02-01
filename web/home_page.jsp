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
<title>Mallard Blog</title>
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
        </c:if>

        <c:if test="${empty sessionScope.get('loggedInUser')}">
            <%--list of blogs--%>
            <div class="col-sm-12 col-md-12 col-lg-12">
                <%@ include file="WEB-INF/_personal_blog_articles.jsp" %>
            </div>
        </c:if>

</div>
</body>
</html>
