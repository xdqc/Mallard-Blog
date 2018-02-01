<%--getServletPath : <%= request.getServletPath() %><br>--%>
<%--getQueryString : <%= request.getQueryString() %><br>--%>
<%--getQueryString : <%= request.getSession().getAttributeNames() %><br>--%>

<c:forEach var="blog" items="${blogs}">
    <c:if test="${blog.getArticle().getShowHideStatus()>0 && blog.getAuthor().getIsvalid()>0}">
        <article class="panel panel-info article-panel" id="article-panel-${blog.getArticle().getId()}">
            <div class="panel-heading">
                <h3 class="article-title"
                    id="article-title-${blog.getArticle().getId()}">${blog.getArticle().getTitle()}</h3>

            </div>
            <div class="panel-body">

                <div class="panel-text article-author">
                    <span class="fa  fa-user"></span> ${blog.getAuthor().getFName()} ${blog.getAuthor().getLName()}
                </div>
                <div class="panel-text article-time">
                                <span class="h5 text-muted"><span class="fa fa-clock-o"></span>
                                        ${blog.getArticle().getCreateTime().toLocalDateTime()}&nbsp;&nbsp;&nbsp;</span>
                    <c:if test="${not empty blog.getArticle().getEditTime()}">
                        <span class="h5 text-muted"> Edited on <span class="fa fa-clock-o"></span>
                                ${blog.getArticle().getEditTime().toLocalDateTime()}&nbsp;&nbsp;&nbsp;</span>
                    </c:if>

                </div>
                <div class="panel-text article-likes">
                        <span class="h5 text-muted">  ${blog.getArticle().getLikeNum()}&nbsp;<span
                                class="fa fa-thumbs-up"></span></span>
                </div>

                <img class="panel-img-top img-responsive" src="https://source.unsplash.com/random/${Math.round((Math.random()*600))+500}x${Math.round((Math.random()*200))+300}"
                     alt="random picture"/>

                    <%--display multimedia gallery here by ajax--%>
                <div id="multimedia-gallery-${blog.getArticle().getId()}"></div>

                <br>
                <div id="article-content-${blog.getArticle().getId()}" class="panel-text article-content">
                        ${blog.getArticle().getContent().substring(0, Math.min(140, blog.getArticle().getContent().length()-1))}

                    <img id="load-article-content-img-${blog.getArticle().getId()}" src="pictures/loading.gif"
                         alt="loading..."
                         width="45" style="display: none;" aria-hidden="true">

                    <button id="read-more-${blog.getArticle().getId()}" class="btn btn-default btn-sm read-more-btn">
                        Read more
                    </button>
                </div>
                <br>
                <div class="edit-article-area" id="edit-article-area-${blog.getArticle().getId()}"></div>
                <br>
                <a href="/File-Upload?articleId=${blog.getArticle().getId()}" class="btn btn-primary">Upload
                    multimedia</a>
                <a href="/multimedia-gallery?articleId=${blog.getArticle().getId()}" class="btn btn-primary">Multimedia
                    Gallery</a>

                    <%--edit and delete article button--%>
                <c:if test="${sessionScope.get('loggedInUser').equals(requestScope.get('browsingUser'))}">
                        <span class="edit-article-btn btn btn-success"
                              id="edit-article-btn-${blog.getArticle().getId()}">
                            <span class="fa fa-pencil"></span> Edit</span>
                    <span class="delete-article-btn btn btn-danger"
                          id="delete-article-btn-${blog.getArticle().getId()}">
                            <span class="fa fa-trash"></span> Delete</span>
                </c:if>

                    <%--<c:if test="${blog.getNumComments() > 0}">--%>
                <button type="button" id="showCommentBtn-${blog.getArticle().getId()}"
                        class="btn btn-info show-comment-btn">
                    <span class="badge" id="num-comments-${blog.getArticle().getId()}"></span>
                    Comments
                    <span id="comment-arrow-${blog.getArticle().getId()}" class="fa fa-chevron-down"></span>
                </button>

                <c:if test="${not empty sessionScope.get('loggedInUser')}">
                    <%@include file="_personal_blog_leave_comment.jsp"%>
                </c:if>

                <div id="comment-area-${blog.getArticle().getId()}" class="widget-area blank comment-area"
                     style="display: none">
                </div>
            </div>
        </article>
        <br>
    </c:if>
</c:forEach>
