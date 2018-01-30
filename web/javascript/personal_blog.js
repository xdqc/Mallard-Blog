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
                        .append($("<dt class='comment'>").html(comment["commenter"] + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
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

                    /*


                     */

                    //This is a special use case of exploiting of var hijacking to access it outside loop
                    var $pp = ($("<dd class='comment'>").text(comment.content)).appendTo($dl)
                        .append(replyBtn).append(replyForm);
                }
            }
        } else {
            //The first elem in commentNode will ALWAYS be a commentObj, so $pp will SURELY be initialized
            showCascadingComments(commentNode, $pp);
        }
    }
};

$(document).ready(function () {


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

});

