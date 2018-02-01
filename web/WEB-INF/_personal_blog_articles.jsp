<%--getServletPath : <%= request.getServletPath() %><br>--%>
<%--getQueryString : <%= request.getQueryString() %><br>--%>
<%--getQueryString : <%= request.getSession().getAttributeNames() %><br>--%>

<c:forEach var="blog" items="${blogs}">
    <c:if test="${blog.getArticle().getShowHideStatus()>0 && blog.getAuthor().getIsvalid()>0}">
        <article class="panel panel-info article-panel" id="article-panel-${blog.getArticle().getId()}">
            <div class="panel-heading">
                <h4 class="panel-title"
                    id="article-title-${blog.getArticle().getId()}">${blog.getArticle().getTitle()}</h4>

            </div>
            <div class="panel-body">

                <div class="panel-text">
                    <span class="fa  fa-user"></span> ${blog.getAuthor().getFName()} ${blog.getAuthor().getLName()}
                </div>
                <div class="panel-text">
                                <span class="h5 text-muted"><span class="fa fa-clock-o"></span>
                                        ${blog.getArticle().getCreateTime().toLocalDateTime()}&nbsp;&nbsp;&nbsp;</span>
                    <span class="h5 text-muted"> ${blog.getArticle().getLikeNum()}&nbsp;<span
                            class="fa fa-thumbs-up"></span></span>
                </div>

                <img class="panel-img-top img-responsive" src="https://picsum.photos/1000/400"
                     alt="random picture"/>

                    <%--display multimedia gallery here by ajax--%>
                <div id="multimedia-gallery-${blog.getArticle().getId()}"></div>

                <br>
                <div id="article-content-${blog.getArticle().getId()}" class="panel-text">
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
                <%--</c:if>--%>
                <%--<c:if test="${blog.getNumComments() == 0}">--%>
                    <%--<button type="button" id="show-comment-btn-${blog.getArticle().getId()}"--%>
                            <%--class="btn btn-info show-comment-btn" disabled="disabled"><span class="badge">0</span>--%>
                        <%--Comments--%>
                    <%--</button>--%>
                <%--</c:if>--%>

                <c:if test="${not empty sessionScope.get('loggedInUser')}">
                    <div class="container leave-comment" id="leave-comment-${blog.getArticle().getId()}">
                        <div class="row">
                            <h4>Leave a comment</h4>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="widget-area no-padding blank">
                                    <div class="status-upload">
                                        <form>
                                            <textarea id="leave-comment-text-${blog.getArticle().getId()}"
                                                      placeholder="What are you thinking about this article?"></textarea>
                                            <ul class="list-unstyled list-inline">
                                                <li><a title="" data-toggle="tooltip" data-placement="bottom"
                                                       data-original-title="Audio"><i class="fa fa-music"></i></a></li>
                                                <li><a title="" data-toggle="tooltip" data-placement="bottom"
                                                       data-original-title="Video"><i
                                                        class="fa fa-video-camera"></i></a></li>
                                                <li><a title="" data-toggle="tooltip" data-placement="bottom"
                                                       data-original-title="Sound Record"><i
                                                        class="fa fa-microphone"></i></a></li>
                                                <li><a title="" data-toggle="tooltip" data-placement="bottom"
                                                       data-original-title="Picture"><i class="fa fa-picture-o"></i></a></li>
                                            </ul>
                                            <button type="submit" class="btn btn-success leave-comment-submit"
                                                    id="leave-comment-submit-${blog.getArticle().getId()}"><i
                                                    class="fa fa-share"></i> Comment
                                            </button>
                                        </form>
                                    </div><!-- Status Upload  -->
                                </div><!-- Widget Area -->
                            </div>
                        </div>
                    </div>
                </c:if>

                <img id="load-comment-img-${blog.getArticle().getId()}" src="pictures/loading.gif" alt="loading..."
                     width="60" style="display: none; padding:10px 0 0 20px" aria-hidden="true">
                <div id="comment-area-${blog.getArticle().getId()}" class="widget-area blank comment-area"
                     style="display: none">
                </div>
            </div>
        </article>
        <br>
    </c:if>
</c:forEach>

<style>



</style>
