<c:forEach var="blog" items="${blogs}">
    <c:if test="${blog.getArticle().getShowHideStatus()>0}">

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
                    ...

                    <button id="read-more-${blog.getArticle().getId()}" class="btn btn-default btn-sm">Read more
                    </button>
                </div>
                <br>

                <c:if test="${blog.getNumComments() > 0}">
                    <button type="button" id="showCommentBtn-${blog.getArticle().getId()}"
                            class="btn btn-info"> Show
                        Comments
                        <span class="badge">${blog.getNumComments()}</span></button>
                </c:if>
                <c:if test="${blog.getNumComments() == 0}">
                    <button type="button" id="showCommentBtn-${blog.getArticle().getId()}"
                            class="btn btn-info"
                            disabled="disabled"> Show Comments
                        <span class="badge">0</span></button>
                </c:if>
                <div id="comment-area-${blog.getArticle().getId()}" class="">
                </div>

            </div>
        </article>
        <br>

        <script type="text/javascript">
            $("#showCommentBtn-${blog.getArticle().getId()}").on("click", function () {
                let commentArea = $("#comment-area-${blog.getArticle().getId()}");
                // Toggle comment-area display by click this button
                $(this).toggleClass('active');

                if ($(this).hasClass('active')) {
                    $.ajax({
                        type: 'POST',
                        url: 'personal-blog',
                        data: {comment: "${blog.getArticle().getId()}"},
                        cache: false,
                        beforeSend: function () {
                            commentArea.text('loading...')
                        },
                        success: function (resp, status) {
                            // TODO make comments display nicely
                            commentArea.text(JSON.stringify(resp));
                        },
                        error: function (msg, status) {
                            commentArea.text(msg);
                        },
                        complete: function () {
                            console.log("loaded");
                        }
                    });

                    commentArea.show();
                } else {
                    commentArea.hide();
                }
            });

            $("#read-more-${blog.getArticle().getId()}").on("click", function () {
                let articleContent = $("#article-content-${blog.getArticle().getId()}");
                $(this).hide();
                $.ajax({
                    type: 'POST',
                    url: 'personal-blog',
                    data: {content: "${blog.getArticle().getId()}"},
                    cache: false,
                    beforeSend: function () {
                        articleContent.append(document.createTextNode('loading...'));
                    },
                    success: function (resp, status) {
                        articleContent.text(resp[${blog.getArticle().getId()}]);
                    },
                    error: function (msg, status) {
                        articleContent.append(document.createTextNode(msg));
                    },
                    complete: function () {
                        console.log("loaded");
                    }
                });
            });
        </script>

    </c:if>
</c:forEach>
