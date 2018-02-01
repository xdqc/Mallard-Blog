<article class="panel panel-info article-panel" id="article-panel-${blog.getArticle().getId()}">
    <div class="panel-heading">
        <h4 class="panel-title"
            id="article-title-${blog.getArticle().getId()}">${blog.getArticle().getTitle()}</h4>

    </div>
    <div class="panel-body">

        <div class="panel-text article-author">
            <span class="fa  fa-user"></span> ${blog.getAuthor().getFName()} ${blog.getAuthor().getLName()}
        </div>
        <div class="panel-text article-time">
                                <span class="h5 text-muted"><span class="fa fa-clock-o"></span>
            ${blog.getArticle().getCreateTime().toLocalDateTime()}&nbsp;&nbsp;&nbsp;</span>

        </div>
        <div class="panel-text article-likes">
            <span class="h5 text-muted"> Edited ${blog.getArticle().getLikeNum()}&nbsp;<span
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


        <%--<c:if test="${blog.getNumComments() > 0}">--%>
        <button type="button" id="showCommentBtn-${blog.getArticle().getId()}"
                class="btn btn-info show-comment-btn">
            <span class="badge" id="num-comments-${blog.getArticle().getId()}"></span>
            Comments
            <span id="comment-arrow-${blog.getArticle().getId()}" class="fa fa-chevron-down"></span>
        </button>


        <div id="comment-area-${blog.getArticle().getId()}" class="widget-area blank comment-area"
             style="display: none">
        </div>
    </div>
</article>
<br>
