<%--
  Created by IntelliJ IDEA.
  User: yyl15
  Date: 27/01/2018
  Time: 1:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="javascript/jquery.js"></script>
    <script type="text/javascript" src="javascript/html5gallery.js"></script>
</head>
<body>
<div style="display:none;" class="html5gallery" data-skin="horizontal" data-width="800" data-height="600">

<c:forEach var="attachment" items="${attachments}">
    <!-- Add images to Gallery -->
    <a href="${attachment.getPath()}/${attachment.getFilename()}.${attachment.getMime()}"><img src="${attachment.getPath()}/${attachment.getFilename()}_thumbnail.jpg" alt="${attachment.getFilename()}"></a>
</c:forEach>
    <!-- Add videos to Gallery -->
    <!--a href="pictures/Big_Buck_Bunny.mp4"><img src="pictures/Big_Buck_Bunny.jpg" alt="Big Buck Bunny, Copyright Blender Foundation"></a -->

    <!-- Add Youtube video to Gallery -->
    <!--a href="http://www.youtube.com/embed/YE7VzlLtp-4"><img src="http://img.youtube.com/vi/YE7VzlLtp-4/2.jpg" alt="Youtube Video"></a-->
</div>

</body>
</html>
