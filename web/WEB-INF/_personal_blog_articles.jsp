getServletPath : <%= request.getServletPath() %><br>
getQueryString : <%= request.getQueryString() %><br>
getQueryString : <%= request.getSession().getAttributeNames() %><br>
<c:forEach var="blog" items="${blogs}">
    <c:if test="${blog.getArticle().getShowHideStatus()>0 && blog.getAuthor().getIsvalid()>0}">
        <article class="panel panel-info">
            <div class="panel-heading">
                <h4 class="panel-title">${blog.getAuthor().getFName()} ${blog.getAuthor().getLName()}</h4>
            </div>
            <img class="panel-img-top img-responsive" src="https://picsum.photos/1000/400"
                 alt="random picture"/>
            <div class="panel-body">
                <div class="panel-text">
                                <span class="h5 text-muted"><span class="fa fa-clock-o"></span>
                                        ${blog.getArticle().getCreateTime().toLocalDateTime()}&nbsp;&nbsp;&nbsp;</span>
                    <span class="h5 text-muted"> ${blog.getArticle().getLikeNum()}&nbsp;<span
                            class="fa fa-thumbs-up"></span></span>
                </div>
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
                <a href="/File-Upload?articleId=${blog.getArticle().getId()}" class="btn btn-primary">Upload multimedia</a>
                <a href="/multimedia-gallery?articleId=${blog.getArticle().getId()}" class="btn btn-primary">Multimedia
                    Gallery</a>

                <c:if test="${blog.getNumComments() > 0}">
                    <button type="button" id="showCommentBtn-${blog.getArticle().getId()}"
                            class="btn btn-info show-comment-btn">
                        <span class="badge">${blog.getNumComments()}</span>
                        Comments
                        <span id="comment-arrow-${blog.getArticle().getId()}" class="fa fa-chevron-down"></span>
                    </button>
                </c:if>
                <c:if test="${blog.getNumComments() == 0}">
                    <button type="button" id="show-comment-btn-${blog.getArticle().getId()}"
                            class="btn btn-info show-comment-btn" disabled="disabled"><span class="badge">0</span>
                        Comments
                    </button>
                </c:if>

                <img id="load-comment-img-${blog.getArticle().getId()}" src="pictures/loading.gif" alt="loading..."
                     width="60" style="display: none; padding:10px 0 0 20px" aria-hidden="true">
                <div id="comment-area-${blog.getArticle().getId()}" class="panel panel-default comment-area"
                     style="display: none">
                </div>
            </div>
        </article>
        <br>
    </c:if>
</c:forEach>

<style type="text/css">
    dl.comment {
        padding: 1em 0 0 2em;
        margin-bottom: 10px;
    }

    dd.comment {
        position: relative;
    }

    a.reply-comment-btn {
        padding-left: 2em;
    }
    a.edit-comment-btn {
        padding-left: 2em;
    }
    a.delete-comment-btn {
        padding-left: 2em;
    }

    .comment-area {
        margin: 10px;
        padding: 10px;
    }

    .reply-text {
        margin: 0 20px 10px 0;
    }
    .edit-text {
        margin: 0 20px 10px 0;
    }

    .popup {
        margin: -15px 0 20px 0;
        padding: 10px;
        background: lightgrey;
        border-radius: 5px;
        width: 80%;
        position: relative;
        top: 20px;
        display: none;
    }

    .popup .close {
        position: absolute;
        top: 10px;
        right: 10px;
        transition: all 200ms;
        font-size: 30px;
        font-weight: bold;
        color: #333;
    }

    .popup .close:hover {
        color: #E6484F;
    }
</style>


