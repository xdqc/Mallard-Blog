<%@page import="db_connector.DbConnector" %>
<li>
    <c:set var="userId" value="${comment.getRoot().getData().getCommenter()}"/>
    <c:set var="user" value="${DbConnector.getUserByUserId(userId)}"/>
    ${user.getFName()} ${user.getLName()} said:
    <br>
    ${comment.getRoot().getData().getContent()}
</li>