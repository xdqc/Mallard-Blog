<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="container">
    <div class="row">
        <div class="col-sm-12 blog-comments">
            <ul>
                <c:forEach var="comment" items="${comments.getChildren()}">
                    <%@include file="WEB-INF/_comment_fragment.jsp" %>
                    <c:if test="${comment.getChildren().size()>0}">
                        <c:set var="comments" value="${comment.getChildren()}"/>
                        <ul>
                            <c:forEach var="comment" items="${comments}">
                                <%@include file="WEB-INF/_comment_fragment.jsp" %>
                                <c:if test="${comment.getChildren().size()>0}">
                                    <c:set var="comments" value="${comment.getChildren()}"/>
                                    <ul>
                                        <c:forEach var="comment" items="${comments}">
                                            <%@include file="WEB-INF/_comment_fragment.jsp" %>
                                            <c:if test="${comment.getChildren().size()>0}">
                                                <c:set var="comments" value="${comment.getChildren()}"/>
                                                <ul>
                                                    <c:forEach var="comment" items="${comments}">
                                                        <%@include file="WEB-INF/_comment_fragment.jsp" %>
                                                        <c:if test="${comment.getChildren().size()>0}">
                                                            <c:set var="comments" value="${comment.getChildren()}"/>
                                                            <ul>
                                                                <c:forEach var="comment" items="${comments}">
                                                                    <%@include file="WEB-INF/_comment_fragment.jsp" %>
                                                                    <c:if test="${comment.getChildren().size()>0}">
                                                                        <c:set var="comments" value="${comment.getChildren()}"/>
                                                                        <ul>
                                                                            <c:forEach var="comment" items="${comments}">
                                                                                <%@include file="WEB-INF/_comment_fragment.jsp" %>
                                                                            </c:forEach>
                                                                        </ul>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </ul>
                                                        </c:if>
                                                    </c:forEach>
                                                </ul>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>





