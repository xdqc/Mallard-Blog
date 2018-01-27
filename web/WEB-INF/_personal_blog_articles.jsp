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

                    <img id="load-article-content-img-${blog.getArticle().getId()}" src="pictures/loading.gif" alt="loading..."
                         width="45" style="display: none;" aria-hidden="true">

                    <button id="read-more-${blog.getArticle().getId()}" class="btn btn-default btn-sm read-more-btn">Read more
                    </button>
                </div>
                <br>

                <c:if test="${blog.getNumComments() > 0}">
                    <button type="button" id="showCommentBtn-${blog.getArticle().getId()}"
                            class="btn btn-info show-comment-btn">
                        <span class="badge">${blog.getNumComments()}</span>
                        Comments
                        <span id="comment-arrow-${blog.getNumComments()}" class="fa fa-chevron-down"></span>
                    </button>
                </c:if>
                <c:if test="${blog.getNumComments() == 0}">
                    <button type="button" id="show-comment-btn-${blog.getArticle().getId()}"
                            class="btn btn-info show-comment-btn" disabled="disabled"><span class="badge">0</span> Comments
                    </button>
                </c:if>

                <img id="load-comment-img-${blog.getArticle().getId()}" src="pictures/loading.gif" alt="loading..."
                     width="60" style="display: none; padding:10px 0 0 20px" aria-hidden="true">
                <div id="comment-area-${blog.getArticle().getId()}" class="panel panel-default" style="display: none">
                </div>
            </div>
        </article>
        <br>
    </c:if>
</c:forEach>

<style type="text/css">
    dl.comment {
        padding-left: 2em;
    }

</style>

<script type="text/javascript">
    /**
     * AJAX comments of article
     */
    $(".show-comment-btn").on("click", function () {
        const articleID = $(this).attr("id").slice($(this).attr("id").lastIndexOf("-") + 1);
        const commentArea = $("#comment-area-" + articleID);
        const loadingImg = $("#load-comment-img-" + articleID);
        const arrow = $("#comment-arrow-" + articleID);
        // Toggle comment-area display by click this button
        $(this).toggleClass('active');

        if ($(this).hasClass('active')) {
            $.ajax({
                type: 'POST',
                url: 'personal-blog',
                data: {comment: articleID},
                cache: false,
                beforeSend: function () {
                    loadingImg.css("display", "block");
                    arrow.removeClass("fa-chevron-down");
                    arrow.addClass("fa-spinner fa-pulse fa-fw");
                },
                success: function (resp) {
                    loadingImg.css("display", "none");
                    commentArea.empty();
                    console.log(resp);

                    // TODO make comments display nicely
                    showCascadingComments(resp, commentArea);

                    //helper function recursively show comment tree
                    function showCascadingComments(commentNode, $p) {
                        $.each(commentNode, function (id, comment) {
                            if (comment !== null && typeof comment === 'object') {
                                const ago = $.timeago(Date.parse(comment["createTime"]));
                                const dl = $("<dl class='comment'>").appendTo($p)
                                    .append($("<dt>").html(comment["commenter"] + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                                        .append($("<span class='text-muted fa fa-clock-o'>")
                                            .append($("<abbr>").attr("title", comment["createTime"]).html("&nbsp;" + ago))));
                                const $pp = ($("<dd>").text(comment.content)).appendTo(dl);
                                showCascadingComments(comment, $pp)
                            }
                        })
                    }

                    commentArea.slideDown();
                    arrow.removeClass("fa-spinner fa-pulse fa-fw");
                    arrow.addClass("fa-chevron-up");

                },
                error: function (msg, status) {
                    console.log("error!!!");
                    console.log(status);
                    console.log(msg);
                },
                complete: function () {
                    console.log("loaded");
                }
            });

        } else {
            commentArea.slideUp();
            arrow.removeClass("fa-chevron-up");
            arrow.addClass("fa-chevron-down");
        }
    });

    /**
     * AJAX article content
     */
    $(".read-more-btn").on("click", function () {
        const articleID = $(this).attr("id").slice($(this).attr("id").lastIndexOf("-") + 1);
        const articleContent = $("#article-content-" + articleID);
        const loadingImg = $("#load-article-content-img-" + articleID);
        $(this).hide();
        $.ajax({
            type: 'POST',
            url: 'personal-blog',
            data: {content: articleID},
            cache: false,
            beforeSend: function () {
                loadingImg.show();
            },
            success: function (resp) {
                loadingImg.hide();
                articleContent.text(resp[articleID]);
            },
            error: function (msg, status) {
                console.log("error!!");
                console.log(status);
                console.log(msg);
            },
            complete: function () {
                console.log("loaded");
            }
        });
    });

</script>
