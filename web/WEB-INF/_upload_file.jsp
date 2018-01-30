<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: yyl15
  Date: 29/01/2018
  Time: 2:14 PM
  To change this template use File | Settings | File Templates.
--%>
<form id="uploadForm" action="/File-Upload${parameterString}" method="post" enctype="multipart/form-data">
    <fieldset id="files">
        <legend>Select your file</legend>
        <input id ="file" type="file" name="file" /><input type="button" value="Add more files" onclick="addFileInput()"><br>
    </fieldset>
    <input type="hidden" name="respondPath" value="<%= request.getServletPath() %>?<%= request.getQueryString() %>">
    <%
        Enumeration names = request.getSession().getAttributeNames();
        while(names.hasMoreElements()){
            String parameterName = (String)names.nextElement();
            Object o = request.getSession().getAttribute(parameterName);
            %><input type="hidden" name="<%=parameterName%>" value="<%= o %>"><%
        }
    %>
    <input type = "submit" value = "Upload">
</form>
<form id="resultForm">
    <c:if test="${not empty uploadedFiles && not uploadedFiles.equals('')}" >
    <fieldset>
        <legend>Your uploaded file</legend>
        ${uploadedFiles}
    </fieldset>
    </c:if>
</form>
