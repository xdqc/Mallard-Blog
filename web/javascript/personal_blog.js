const entityId = e => e.attr("id").slice(e.attr("id").lastIndexOf("-") + 1);

//helper function recursively show comment tree
function showCascadingComments(commentArr, $p) {
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
                        //.attr("href", "#popup-reply-" + cmtId)
                        .text(" reply");

                    const replyForm = $("<form class='popup'>").attr("id", "popup-reply-" + cmtId)
                        .append(($("<textarea class='reply-text form-control' rows='2' required>")
                            .attr("id", "reply-text-" + cmtId))
                            .attr("placeholder", "Reply to " + comment["commenter"]))
                        .append(($("<input type='submit' class='reply-submit btn btn-success' value='Reply'>")
                            .attr("id", "reply-submit-" + cmtId)))
                        .append($("<a class='close' href='#/'>").html("&times;"));

                    const editBtn = $("<a class='edit-comment-btn fa fa-pencil-square-o'>")
                        .attr("id", "delete-comment-btn-" + cmtId)
                        //.attr("href", "#popup-edit-" + cmtId)
                        .text(" edit");

                    const editForm = $("<form class='popup'>").attr("id", "popup-edit-" + cmtId)
                        .append(($("<textarea class='edit-text form-control' rows='1' required>")
                            .attr("id", "edit-text-" + cmtId))
                            .val(comment["content"]))
                        .append(($("<input type='submit' class='edit-submit btn btn-success' value='Edit'>")
                            .attr("id", "edit-submit-" + cmtId)))
                        .append($("<a class='close' href='#/'>").html("&times;"));

                    const deleteBtn = $("<a class='edit-comment-btn fa fa-pencil-square-o'>")
                        .attr("id", "delete-comment-btn-" + cmtId)
                        //.attr("href", "#popup-delete-" + cmtId)
                        .text(" delete");

                    const deleteForm = $("<form class='popup'>").attr("id", "popup-delete-" + cmtId)
                        .append(($("<input type='submit' class='delete-submit btn btn-danger' value='Delete'>")
                            .attr("id", "delete-submit-" + cmtId)))
                        .append(($("<input type='button' class='delete-submit btn btn-default' value='Cancel'>")
                            .attr("id", "delete-cancel-" + cmtId)));
                        //.append($("<a class='close' href='#/'>").html("&times;"));

                    /*


                     */

                    //This is a special use case of exploiting of var hijacking to access it outside loop
                    var $pp = ($("<dd class='comment'>").text(comment.content)).appendTo($dl);

                    if (loggedInUser !== 0) {
                        $pp.append(replyBtn).append(replyForm);
                    }
                    if (loggedInUser === comment["commenterId"]) {
                        $pp.append(editBtn).append(editForm);
                    }
                    if (loggedInUser === comment["articleAuthorId"] || loggedInUser === comment["commenterId"]) {
                        $pp.append(deleteBtn).append(deleteForm);
                    }
                }
            }
        } else {
            //The first elem in commentNode will ALWAYS be a commentObj, so $pp will SURELY be initialized
            showCascadingComments(commentNode, $pp);
        }
    }
}


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
                    console.log(loggedInUser);
                    commentActions();
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

    /**
     * Functions to handle comment buttons clicks
     */
    function commentActions() {
        $(".reply-comment-btn").on("click", function (e) {
            e.preventDefault();
            console.log($(this));
            const cmtId = entityId($(this));
            const replyForm = $("#popup-reply-" + cmtId);
            replyForm.slideDown();
            console.log(replyForm);
            $(".close").on("click", function () {
                replyForm.slideUp();
            })
        });

        $(".edit-comment-btn").on("click", function (e) {
            e.preventDefault();
            const cmtId = entityId($(this));
            const editForm = $("#popup-edit-" + cmtId);
            editForm.slideDown();
            console.log(editForm);
            $(".close").on("click", function () {
                editForm.slideUp();
            })
        });
    }

});

