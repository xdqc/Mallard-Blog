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

                    <img id="load-article-content-img-${blog.getArticle().getId()}" src="pictures/loading.gif"
                         alt="loading..."
                         width="45" style="display: none;" aria-hidden="true">

                    <button id="read-more-${blog.getArticle().getId()}" class="btn btn-default btn-sm read-more-btn">
                        Read more
                    </button>
                </div>
                <br>

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
                <div id="comment-area-${blog.getArticle().getId()}" class="panel panel-default comment-area" style="display: none">
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

    dd {
        position: relative;
    }

    a.reply-comment-btn {
        padding-left: 2em;
    }
    .comment-area{
        margin: 10px;
        padding: 10px;
    }
    .reply-text{
        margin: 0 20px 10px 0;
    }
    .popup {
        margin: -15px 0 20px 0;
        padding: 10px;
        background: lightgrey;
        border-radius: 5px;
        width: 80%;
        position: relative;
        top:20px;
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
        color: #06D85F;
    }
</style>



<script type="text/javascript">

    const entityId = e => e.attr("id").slice(e.attr("id").lastIndexOf("-") + 1);

    //helper function recursively show comment tree
    const showCascadingComments = (commentArr, $p) => {
        // To exploit var hijacking, do not use arrow functions, just use for loop.
        for (let commentNode of commentArr) {
            if (!$.isArray(commentNode)) {
                for (let cmtId in commentNode) {
                    // get a value of json without pre-knowing its key
                    if (commentNode.hasOwnProperty(cmtId)) {
                        const comment = commentNode[cmtId];
                        const ago = $.timeago(Date.parse(comment["createTime"]));
                        const $dl = $("<dl class='comment'>").appendTo($p)
                            .append($("<dt>").html(comment["commenter"] + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                                .append($("<span class='text-muted fa fa-clock-o'>")
                                    .append($("<abbr>").attr("title", comment["createTime"]).html("&nbsp;" + ago))));

                        const replyBtn = $("<a class='reply-comment-btn fa fa-comments-o'>")
                            .attr("id", "reply-comment-btn-" + cmtId)
                            .attr("href", "#popup-reply-" + cmtId)
                            .text(" reply");

                        const replyForm = $("<form class='popup'>").attr("id", "popup-reply-" + cmtId)
                            .append(($("<textarea class='reply-text form-control' rows='2' required>")
                                .attr("id", "reply-text-" + cmtId))
                                .attr("placeholder", "Reply to "+ comment["commenter"]))
                            .append(($("<input type='submit' class='reply-submit btn btn-primary' value='reply'>")
                                .attr("id", "reply-submit-" + cmtId)))
                            .append($("<a class='close' href='#/'>").html("&times;"));

                        //This is a special use case of exploiting of var hijacking to access it outside loop
                        var $pp = ($("<dd>").text(comment.content)).appendTo($dl)
                            .append(replyBtn).append(replyForm);
                    }
                }
            } else {
                //The first elem in commentNode will ALWAYS be a commentObj, so $pp will SURELY be initialized
                showCascadingComments(commentNode, $pp);
            }
        }
    };


    /**
     * AJAX comments of article
     */
    $(".show-comment-btn").on("click", function () {
        const articleID = entityId($(this));
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
                    resp.forEach(comments => showCascadingComments(comments, commentArea));
                    commentArea.slideDown();
                    arrow.removeClass("fa-spinner fa-pulse fa-fw");
                    arrow.addClass("fa-chevron-up");
                },
                error: (msg, status) => {
                    console.log("error!!!");
                    console.log(status);
                    console.log(msg);
                },
                complete: () => {
                    removeReplyBtn();
                    showReply();
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
        const articleID = entityId($(this));
        const articleContent = $("#article-content-" + articleID);
        const loadingImg = $("#load-article-content-img-" + articleID);
        $(this).hide();
        $.ajax({
            type: 'POST',
            url: 'personal-blog',
            data: {content: articleID},
            cache: false,
            beforeSend: () => {
                loadingImg.show();
            },

            success: resp => {
                loadingImg.hide();
                articleContent.text(resp[articleID]);
            },
            error: (msg, status) => {
                console.log("error!!");
                console.log(status);
                console.log(msg);
            },
            complete: () => {
                console.log("loaded");
            }
        });
    });



</script>
