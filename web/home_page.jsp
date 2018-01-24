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
<%@ include file="head.jsp" %>
<body>
<%@ include file="home_page_logo.jsp" %>
<%@ include file="home_page_menu.jsp" %>
<div>
    <%@ include file="home_page_articles.jsp" %>
    <c:if test="${not empty param.userId}">
    <%@ include file="home_page_profile.jsp" %>
    </c:if>
</div>
</body>
</html>
