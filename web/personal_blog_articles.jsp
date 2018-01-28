<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-9 col-lg-9">

            <c:forEach var="blog" items="${blogs}">
                <c:if test="${blog.getArticle().getShowHideStatus()>0}">

                    <article class="panel panel-info">
                        <div class="panel-heading">
                            <h4 class="panel-title">${blog.getAuthor().getFName()} ${blog.getAuthor().getLName()}</h4>
                        </div>
                        <img class="panel-img-top img-responsive" src="https://picsum.photos/1000/400"
                             alt="random picture"/>
                        <div class="panel-body">

                            <h6 class="text-muted"> ${blog.getArticle().getLikeNum()} <span
                                    class="fa fa-thumbs-up"></span></h6>

                            <p class="panel-text">${blog.getArticle().getContent()}</p>

                            <a href="#" class="btn btn-primary">Read more</a>

                            <a href="/multimedia-gallery?articleId=${blog.getArticle().getId()}" class="btn btn-primary">Multimedia Gallery</a>

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
                            var commentArea = $("#comment-area-${blog.getArticle().getId()}");
                            // Toggle comment-area display by click this button
                            $(this).toggleClass('active');

                            if ($(this).hasClass('active')) {
                                $.ajax({
                                    type: 'POST',
                                    url: 'personal-blog?=',
                                    data: {blog: "${blog.getArticle().getId()}"},
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

                        })
                    </script>

                </c:if>
            </c:forEach>
        </div>
    </div>
</div>
