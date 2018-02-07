<%--
  Created by IntelliJ IDEA.
  User: qd16
  Date: 4/02/2018
  Time: 12:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--suppress SyntaxError --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@include file="_head.jsp" %>
    <title>Administrator's Pleasure Hub</title>
</head>
<body>
<%@ include file="_home_page_menu.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-12">
            <table id="user-table" class="tablesorter table table-striped table-hover  table-responsive">
                <caption id="user-management">User Management</caption>
                <thead>
                <tr>
                    <th>User ID</th>
                    <th>Username</th>
                    <th>Last Name</th>
                    <th>First Name</th>
                    <th>Email</th>
                    <th>Gender</th>
                    <th>DOB</th>
                    <th>Country</th>
                    <th>Create Time</th>
                    <th>Valid Status</th>
                    <th>Delete/Recover</th>
                    <th>Reset Password</th>
                </tr>
                </thead>
                <tbody id="user-table-body">
                <c:forEach var="user" items="${userResults}">
                    <tr>
                        <td id="user-id-${user.getId()}" class="user-id">${user.getId()}</td>
                        <td id="username-${user.getId()}" class="username markCol">${user.getUserName()}</td>
                        <td>${user.getLName()}</td>
                        <td>${user.getFName()}</td>
                        <td id="email-address-${user.getId()}" class="email-address">${user.getEmail()}</td>
                        <td>${user.getGender()==0?"Female":(user.getGender()==1?"Male":"Other")}</td>
                        <td>${user.getDob()}</td>
                        <td>${user.getCountry()}</td>
                        <td>${user.getCreateTime()}</td>
                        <td>${user.getIsvalid()==0?"Deleted":"Active"}</td>
                        <td>
                            <c:if test="${user.getIsvalid()==1}">
                                <button id="delete-user-${user.getId()}" class="delete-user btn btn-sm btn-danger">Delete</button>
                            </c:if>
                            <c:if test="${user.getIsvalid()==0}">
                                <button id="recover-user-${user.getId()}" class="recover-user btn btn-sm btn-success">Recover</button>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${user.getIsvalid()==1}">
                                <button id="send-email-${user.getId()}" class="send-email btn btn-sm btn-info">Send email</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <form id="add-new-user-form" method="post" action="sign-up?signUp=1">
                        <td>Add new user</td>
                        <td><input type="text" name="userName" class="add-user-text"></td>
                        <td><input type="text" name="lname" class="add-user-text"></td>
                        <td><input type="text" name="fname" class="add-user-text"></td>
                        <td><input type="text" name="email" class="add-user-text"></td>
                        <td><select  class="form-control" required id="inputGender" name="gender" >
                            <option name="gender" selected>Please select</option>
                            <option value="0" name="gender">Female</option>
                            <option value="1" name="gender">Male</option>
                            <option value="2" name="gender">Other</option>
                        </select></td>
                        <td><input class="form-control" type="date" name="dob" id="inputDob" required></td>
                        <td><input type="text" name="country" class="add-user-text"></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><button type="submit" id="add-new-user-admin" class="add-new-user-admin btn btn-sm btn-primary">Add</button>
                        </td>
                    </form>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <br>
    <br>
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-12">
            <table id="article-table"
                   class="tablesorter table table-striped table-hover table-bordered table-responsive">
                <caption id="article-management">Article Management</caption>
                <thead>
                <tr>
                    <th>Article ID</th>
                    <th>Title</th>
                    <th>Author Username</th>
                    <th>Create Time</th>
                    <th>Edit Time</th>
                    <th>Valid Time</th>
                    <th>Likes</th>
                    <th>Abuse Reports</th>
                    <th>Status</th>
                    <th>Show/Hide</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="article" items="${articleResults}">
                    <tr>
                        <td>${article.getVal1().getId()}</td>
                        <td class="markCol">${article.getVal1().getTitle()}</td>
                        <td>${article.getVal2().getUserName()}</td>
                        <td>${article.getVal1().getCreateTime()}</td>
                        <td>${article.getVal1().getEditTime()}</td>
                        <td>${article.getVal1().getValidTime()}</td>
                        <td>${article.getVal1().getLikeNum()}</td>
                        <td>${article.getVal1().getAbuseNum()}</td>
                        <td>${article.getVal1().getShowHideStatus()==1?"Visable":"Hidden"}</td>
                        <td>
                            <c:if test="${article.getVal1().getShowHideStatus()==1}">
                                <button id="hide-article-${article.getVal1().getId()}" class="hide-article btn btn-sm btn-danger">Hide It</button>
                            </c:if>
                            <c:if test="${article.getVal1().getShowHideStatus()==0}">
                                <button id="show-article-${article.getVal1().getId()}" class="show-article btn btn-sm btn-success">Show It</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <br>
    <br>
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-12">
            <table id="comment-table"
                   class="tablesorter table table-striped table-hover table-bordered table-responsive">
                <caption id="comment-management">Comment Management</caption>
                <thead>
                <tr>
                    <th>Comment ID</th>
                    <th>Content</th>
                    <th>Commenter Username</th>
                    <th>Create Time</th>
                    <th>Edit Time</th>
                    <th>Reply to Article ID</th>
                    <th>Reply to Comment ID</th>
                    <th>Abuse Reports</th>
                    <th>Status</th>
                    <th>Show/Hide</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="comment" items="${commentResults}">
                    <tr>
                        <td>${comment.getVal1().getId()}</td>
                        <td class="markCol">${comment.getVal1().getContent()}</td>
                        <td>${comment.getVal2().getUserName()}</td>
                        <td>${comment.getVal1().getCreateTime()}</td>
                        <td>${comment.getVal1().getEditTime()}</td>
                        <td>${comment.getVal1().getParentArticle()}</td>
                        <td>${comment.getVal1().getParentComment()}</td>
                        <td>${comment.getVal1().getAbuseNum()}</td>
                        <td>${comment.getVal1().getShowHideStatus()==1?"Visible":"Hidden"}</td>
                        <td>
                            <c:if test="${comment.getVal1().getShowHideStatus()==1}">
                                <button id="hide-comment-${comment.getVal1().getId()}" class="hide-comment btn btn-sm btn-danger">Hide It</button>
                            </c:if>
                            <c:if test="${comment.getVal1().getShowHideStatus()==0}">
                                <button id="show-comment-${comment.getVal1().getId()}" class="show-comment btn btn-sm btn-success">Show It</button>
                            </c:if>
                        </td>
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
            headers:{
                8: {sorter: 'text'},
                10: {sorter: false},
                11: {sorter: false}
            }
        });
        $("#article-table").tablesorter({
            headers: {
                3: {sorter: 'text'},
                4: {sorter: 'text'},
                5: {sorter: 'text'},
                9: {sorter: false},
            }
        });
        $("#comment-table").tablesorter({
            headers: {
                3: {sorter: 'text'},
                4: {sorter: 'text'},
                9: {sorter: false},
            }
        });
    });


</script>
<style>
    /*do not delete*/
    th.tablesorter-headerAsc {
        cursor: pointer;
        background: url(../pictures/small_asc.png) no-repeat center right;
        background-size: 10px;
    }

    th.tablesorter-headerDesc {
        cursor: pointer;
        background: url(../pictures/small_desc.png) no-repeat center right;
        background-size: 10px;
    }

    th.tablesorter-headerUnSorted {
        cursor: pointer;
        background: url(../pictures/small.png) no-repeat center right;
        background-size: 10px;
    }
    th.sorter-false{
        cursor: default;
        background: none;
    }

    mark {
        background: orange;
        color: black;
    }

    caption {
        padding-top: 20px;
        padding-bottom: 20px;
        color: #666;
        text-align: left;
        font-family: Lato, Helvetica, sans-serif;
        font-size: 36px;
    }

    #user-table td, #user-table th {
        min-width: 100px;
        max-width: 200px;
    }
    #add-new-user-form input{
        max-width:100px;
    }
</style>