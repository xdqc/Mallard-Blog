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
    <title>Search Results</title>
</head>
<body>
<%@ include file="WEB-INF/_home_page_menu.jsp" %>

<div class="container">
    <div class="row">
        <h3>You are searching : <span class="mark" id="searchStr">${searchStr}</span></h3>
        <div class="col-sm-0 col-md-1 col-lg-1"></div>
        <div class="col-sm-12 col-md-10 col-lg-10">


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
                        <td class="markCol"><a href="personal-blog?userId=${user.getId()}">${user.getUserName()}</td>
                        <td>${user.getLName()}</td>
                        <td>${user.getFName()}</td>
                        <td>${user.getEmail()}</td>
                        <td>${user.getGender()==0?"Female":"Male"}</td>
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
        <div class="col-sm-0 col-md-1 col-lg-1"></div>
        <div class="col-sm-12 col-md-10 col-lg-10">

            <table id="article-table" class="tablesorter table table-striped table-hover table-bordered table-responsive">
                <caption>List of found articles</caption>
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Author Last Name</th>
                    <th>Author First Name</th>
                    <th>Create Time</th>
                    <th>Edit Time</th>
                    <th>Valid Time</th>
                    <th>Likes</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="article" items="${articleResults}">
                    <tr>
                        <td class="markCol"><a href="personal-blog?userId=${article.getVal2().getId()}#article-title-${article.getVal1().getId()}">${article.getVal1().getTitle()}</a></td>
                        <td>${article.getVal2().getLName()}</td>
                        <td>${article.getVal2().getFName()}</td>
                        <td class="time create-time">${article.getVal1().getCreateTime()}</td>
                        <td class="time edit-time">${article.getVal1().getEditTime()}</td>
                        <td class="time valid-time">${article.getVal1().getValidTime()}</td>
                        <td>${article.getVal1().getLikeNum()}</td>
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
    $(function () {
        $("#user-table").tablesorter({

        });
        $("#article-table").tablesorter({
            headers: {
                3: {sorter: 'text'},
                4: {sorter: 'text'},
                5: {sorter: 'text'}
            }
        });
    });

    $(document).ready(function () {
        const str = $("#searchStr").html()
        $(".markCol").mark(str);
    })
</script>
<style>
    th.tablesorter-headerAsc {
        cursor: pointer;
        background: url(pictures/small_asc.png) no-repeat center right;
        background-size: 10px;
    }

    th.tablesorter-headerDesc {
        cursor: pointer;
        background: url(pictures/small_desc.png) no-repeat center right;
        background-size: 10px;
    }

    th.tablesorter-headerUnSorted {
        cursor: pointer;
        background: url(pictures/small.png) no-repeat center right;
        background-size: 10px;
    }
    mark {
        background: orange;
        color: black;
    }
</style>