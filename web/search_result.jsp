<%--
  Created by IntelliJ IDEA.
  User: qd16
  Date: 2/1/2018
  Time: 2:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@include file="WEB-INF/_head.jsp" %>
    <title>Title</title>
</head>
<body>
<%@ include file="WEB-INF/_home_page_menu.jsp" %>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-9 col-lg-9">
            <table id="user-table" class="tablesorter table table-striped table-hover  table-responsive">
                <caption>List of found users</caption>
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Last Name</th>
                    <th>First Name</th>
                    <th>Email</th>
                    <th>Gender</th>
                    <th>DOB</th>
                    <th>Country</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userResults}">
                        <tr>
                            <td>${user.getUserName()}</td>
                            <td>${user.getLName()}</td>
                            <td>${user.getFName()}</td>
                            <td>${user.getEmail()}</td>
                            <td>${user.getGender()==0?"Femail":"Male"}</td>
                            <td>${user.getDob()}</td>
                            <td>${user.getCountry()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <br>
    <br>
    <div class="row">
        <div class="col-sm-12 col-md-9 col-lg-9">

            <table id="article-table" class="tablesorter table table-striped table-hover  table-responsive">
                <caption>List of found articles</caption>
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Last Name</th>
                    <th>First Name</th>
                    <th>Create Time</th>
                    <th>Edit Time</th>
                    <th>Valid Time</th>
                    <th>Likes</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="article" items="${articleResults}">
                    <tr>
                        <td>${article.getTitle()}</td>
                        <td>${article.getAuthor()}</td>
                        <td>${article.getAuthor()}</td>
                        <td>${article.getCreateTime()}</td>
                        <td>${article.getEditTime()}</td>
                        <td>${article.getValidTime()}</td>
                        <td>${article.getLikeNum()}</td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>

<script>
    $(function(){
        $("#user-table").tablesorter();
        $("#article-table").tablesorter();
    });
</script>