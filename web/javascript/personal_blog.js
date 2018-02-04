"use strict";

/**
 --  ██╗   ██╗████████╗██╗██╗     ███████╗
 --  ██║   ██║╚══██╔══╝██║██║     ██╔════╝
 --  ██║   ██║   ██║   ██║██║     ███████╗
 --  ██║   ██║   ██║   ██║██║     ╚════██║
 --  ╚██████╔╝   ██║   ██║███████╗███████║
 --   ╚═════╝    ╚═╝   ╚═╝╚══════╝╚══════╝
 */
/**
 * get the id of the entity of which a html element represents
 */
const entityId = e => e.attr("id").slice(e.attr("id").lastIndexOf("-") + 1);

/**
 * Recursive function show comment tree
 */
const showCascadingComments = (commentTree, $parent, numComments) => {
    // To exploit var hijacking, use plain for loop rather than map, reduce, filter...
    for (let commentArr of commentTree) {
        if (!$.isArray(commentArr)) {
            for (let cmtId in commentArr) {
                // get a value of json without pre-knowing its key
                if (commentArr.hasOwnProperty(cmtId)) {
                    // increment comment number for commentTree node which is a comment
                    numComments.num++;
                    const comment = commentArr[cmtId];
                    const ago = $.timeago(Date.parse(comment["createTime"]));

                    // commenter avatar, name and post time
                    var $dl = $("<dl class='comment widget-area '>").appendTo($parent)
                        .append($("<dt class='comment'>")
                            .append($("<a>").attr("href", "personal-blog?userId="+comment["commenterId"])
                                .html("&nbsp;&nbsp;"+comment["commenter"])
                                .prepend(
                                    $("<img class='img-circle' alt='Card image cap'>")
                                        .attr("src", "http://i.pravatar.cc/40?u="+comment["commenterId"])
                                )
                            )
                            .append($("<span>").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"))
                            .append($("<span class='text-muted fa fa-clock-o'>")
                                .append($("<abbr>").attr("title", comment["createTime"]).html("&nbsp;" + ago))));

                    const replyBtn = $("<a class='reply-comment-btn'>")
                        .attr("id", "reply-comment-btn-" + cmtId)
                        .text(" Reply")
                        .prepend($("<span class='fa fa fa-comments-o'>"));

                    const replyForm = $("<form class='popup'>").attr("id", "popup-reply-" + cmtId)
                        .append(($("<textarea class='reply-text form-control' rows='2' required>")
                            .attr("id", "reply-text-" + cmtId))
                            .attr("placeholder", "Reply to " + comment["commenter"]))
                        .append(($("<button type='submit' class='reply-submit btn btn-success'> Reply</button>")
                            .prepend($("<span class='fa fa-comments-o'>"))
                            .attr("id", "reply-submit-" + cmtId)))
                        .append($("<a class='close' href='#/'>").html("&times;"));

                    const editBtn = $("<a class='edit-comment-btn '>")
                        .attr("id", "delete-comment-btn-" + cmtId)
                        .text(" Edit")
                        .prepend($("<span class='fa fa-pencil-square-o'>"));

                    const editForm = $("<form class='popup'>").attr("id", "popup-edit-" + cmtId)
                        .append(($("<textarea class='edit-text form-control' rows='2' required>")
                            .attr("id", "edit-text-" + cmtId))
                            .val(comment["content"]))
                        .append(($("<button type='submit' class='edit-submit btn btn-success'> Edit</button>")
                            .prepend($("<span class='fa fa-pencil-square-o'>"))
                            .attr("id", "edit-submit-" + cmtId)))
                        .append($("<a class='close' href='#/'>").html("&times;"));

                    const deleteBtn = $("<a class='delete-comment-btn'>")
                        .attr("id", "delete-comment-btn-" + cmtId)
                        .text(" Delete")
                        .prepend($("<span class='fa fa-trash-o'>"));


                    /*


                     */

                    //This is a special use case of exploiting of var hijacking to access it outside loop
                    const $dd = ($("<dd class='comment status-upload'>").text(comment.content)).appendTo($dl);
                    const $ddd = ($("<div class='comment-action-btn'>")).appendTo($dd)
                        .append(replyBtn).append(editBtn).append(deleteBtn);

                    if (loggedInUser !== comment["articleAuthorId"] && loggedInUser !== comment["commenterId"]) {
                        deleteBtn.off("click").addClass("disabled");
                    }
                    if (loggedInUser !== comment["commenterId"]) {
                        editBtn.off("click").addClass("disabled");
                    }
                    if (loggedInUser === 0) {
                        replyBtn.off("click").addClass("disabled");
                    }
                    if (loggedInUser !== 0) {
                        $dd.append(replyForm);
                    }
                    if (loggedInUser === comment["commenterId"]) {
                        $dd.append(editForm);
                    }
                }
            }
        } else {
            //The first elem in commentArr will ALWAYS be a commentObj, so $dl will be initialized
            showCascadingComments(commentArr, $dl, numComments);
        }
    }
};

/**
 * Functions handle creating and editing articles
 */
const articleFocusOn = (id) => {
    console.log(id + " is working on?");

    const datePicker = $("#publish-time-" + id);
    const publishBtn = $("#publish-" + id);
    const publishMode = $("#publish-mode-" + id);
    const title = $("#input-article-title-" + id);
    const content = $("#input-article-content-" + id);

    //WYSIWYG
    title.attr("value", $("#article-title-" + id).text());    //don't val(), it will bind the value and wont change later
    content.text($("#article-content-" + id).text());

    return {
        "datePicker": datePicker,
        "publishBtn": publishBtn,
        "publishMode": publishMode,
        "title": title,
        "content": content
    }
};



$(document).ready(function () {
    /**
     --   █████╗ ██████╗ ████████╗██╗ ██████╗██╗     ███████╗
     --  ██╔══██╗██╔══██╗╚══██╔══╝██║██╔════╝██║     ██╔════╝
     --  ███████║██████╔╝   ██║   ██║██║     ██║     █████╗
     --  ██╔══██║██╔══██╗   ██║   ██║██║     ██║     ██╔══╝
     --  ██║  ██║██║  ██║   ██║   ██║╚██████╗███████╗███████╗
     --  ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝╚══════╝╚══════╝
     */
    //Create new article area works on load.
    articleActions(0);

    /**
     * Ajax load articles on homepage
     */
    const loadedArticles = {num: $(".article-panel").length};
    $("#load-more-articles").on("click", function () {
        const moreArticleArea = $("#more-article-area");
        $.ajax({
            type: 'post',
            url: 'personal-blog',
            data: {
                loadMoreArticles: loadedArticles.num,
                //loadArticleAuthoredBy: entityId($(".user-panel")),
            },
            cache: false,
            beforeSend: () => {
                loadedArticles.num++;
                $("#load-article-img").css("display","block");
            },
            success: (resp) => {
                let html = moreArticleArea.html();
                html += resp;
                moreArticleArea.html(html);

                //no more articles
                if (resp.startsWith("<h3")){
                    $(this).css("display","none");
                }
            },
            error: (msg, status) => {
                loadedArticles.num--;
                console.log("error of loading more article!!!");
                console.log(status);
                console.log(msg);
            },
            complete: (resp, status) => {
                $("#load-article-img").css("display", "none");

                // // register event
                // resp = resp["responseText"];
                // const articleId = resp.split(/\r?\n/)[0].slice(resp.split(/\r?\n/)[0].lastIndexOf('-') + 1).slice(0, -2);
                // const $articlePanel = $("#article-panel-" + articleId);
                // if (!$articlePanel.hasClass("clickable-active")) {
                //     articleActions(articleId);
                //     $articlePanel.addClass("clickable-active");
                // }
            }
        })
    });

    /**let article actions 'focus on' particular article (or none for creating new)*/
    const articleActionsHandler = (trigger) => {
        trigger.on("click", function () {
            const articleId = entityId($(this));
            $(".panel-collapse").collapse('hide');
            return articleFocusOn(articleId);
        })
    };
    articleActionsHandler($(".accordion-bar"));

    /**
     * AJAX load article content
     */
    $(document).on("click", ".read-more-btn", function () {
        const articleID = entityId($(this));
        const articleContent = $("#article-content-" + articleID);
        const loadingImg = $("#load-article-content-img-" + articleID);
        $(this).hide();
        $.ajax({
            type: 'POST',
            url: 'personal-blog',
            data: {loadContentOfArticle: articleID},
            cache: false,
            beforeSend: () => {
                loadingImg.show();
            },

            success: resp => {
                loadingImg.hide();
                articleContent.html(resp[articleID]);
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
     * Ajax load edit article area
     */
    $(".edit-article-btn").on("click", function () {

        const articleId = entityId($(this));
        const $articlePanel = $("#article-panel-"+articleId);
        const editArea = $("#edit-article-area-" + articleId);
        $("#read-more-"+articleId).click();
        $.ajax({
            type: 'POST',
            url: 'personal-blog',
            data: {editArticle: articleId},
            cache: false,
            beforeSend: () => {
            },
            success: (resp) => {
                editArea.html(resp);
            },
            error: (msg, status) => {
                console.log("error of loading edit area!!!");
                console.log(status);
                console.log(msg);
            },
            complete: () => {
                articleActionsHandler($(".accordion-bar"));
                // event binding for ajax loaded area
                if (!$articlePanel.hasClass("clickable-active")){
                    articleActions(articleId);
                    $articlePanel.addClass("clickable-active");
                }
            }
        })
    });

    function articleActions(articleId) {
        const $articlePanel = $("#article-panel-"+articleId);

        /**
         * choose post type: publish/draft
         */
        $articlePanel.on("change", ".publish-mode", function () {

            const articleId = entityId($(this));

            const form = articleFocusOn(articleId);

            if (form["publishMode"][0].value === "publish") {
                form["datePicker"].hide();
                form["publishBtn"].empty();
                form["publishBtn"].text(" Publish")
                    .prepend($("<span class='fa fa-paper-plane' aria-hidden='true'>"));

            } else if (form["publishMode"][0].value === "draft") {
                form["datePicker"].show();
                form["publishBtn"].empty();
                form["publishBtn"].text(" Save")
                    .prepend($("<span class='fa fa-floppy-o' aria-hidden='true'>"));
                let date = new Date();
                date = moment(date).format("YYYY-MM-DDTkk:mm");
                form["datePicker"].val(date);
            }
        });

        /**
         * Post a new article or edit existing article
         */
        $articlePanel.on("click", ".publish", function (e) {
            e.preventDefault();
            const articleId = entityId($(this));

            const form = articleFocusOn(articleId);

            const uploadingImg = $("img.uploading-img");

            // validate article title
            if (form["title"].val() === "") {
                swal({
                    title: "Need a title!",
                    text: "Write something interesting:",
                    type: "input",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    inputPlaceholder: "Write title"
                }, function (inputValue) {
                    if (inputValue === false) return false;
                    if (inputValue === "") {
                        swal.showInputError("You need to write a title!");
                        return false;
                    }
                    swal("Nice!", "The title: " + inputValue, "success");
                    form["title"].val(inputValue);
                });
                return;
            }

            // validate article content
            if (form["content"].val() === "" || form["content"].val() === undefined) {
                swal("Write something!", "You need have some content!", "warning");
                return;
            }

            let availableDate = new Date();

            // if user select to draft, validate available time
            if (form["publishMode"][0].value === "draft") {
                if (form["datePicker"].val() === "" || form["datePicker"].val() === undefined) {
                    alert("Available time is required.");
                    return;
                }
                let date = form["datePicker"].val();
                date = moment(date).format("YYYY-MM-DDTkk:mm");
                availableDate = new Date(date);
            }

            const article = {};
            article["articleId"] = articleId > 0 ? articleId : "0";
            article["title"] = form["title"].val();
            article["content"] = form["content"].val().replace(/\r?\n/g, '<br />');
            article["authorId"] = loggedInUser;
            article["createTime"] = new Date().getTime();
            article["validTime"] = availableDate.getTime();

            console.log(article);

            //Ajax post to servlet
            $.ajax({
                type: 'POST',
                url: 'personal-blog',
                data: {newArticle: JSON.stringify(article)},
                cache: false,
                beforeSend: () => {
                    uploadingImg.show();
                },

                success: resp => {
                    form["title"].val("");
                    form["content"].val("");

                    let msg;
                    if (resp.startsWith("inserted")) {
                        msg = form["publishMode"][0].value === "publish" ? "Your article are published."
                            : "Your article will be visible to public on " + availableDate.toLocaleString();
                        swal("Congratulations ", msg, "success");
                    } else if (resp.startsWith("updated")) {
                        msg = form["publishMode"][0].value === "publish" ? "Your article are updated."
                            : "Your updated article will be visible to public on " + availableDate.toLocaleString();
                        swal("Congratulations ", msg, "success");
                    } else {
                        msg = "Server error";
                        swal("Oops ", msg, "danger")
                    }

                    /*upload file for newly created article or existing article*/
                    const uploadArticleId = resp.startsWith("inserted") ? resp.split(" ")[1] : articleId;

                    //change the uploadArea form $div action parameter to newly created articleId
                    $("#uploadForm-"+articleId).attr("action", "/File-Upload?articleId="+uploadArticleId);
                    $("#uploadButton-comment-"+articleId).click();

                },
                error: (msg, status) => {
                    console.log("error publishing article!!");
                    console.log(status);
                    console.log(msg);
                    swal("Oops ", status, "danger")
                },
                complete: () => {
                    uploadingImg.hide();
                }

            })
        });

        /**
         * delete an article
         */
        $articlePanel.on("click", ".delete-article-btn", function () {
            const articleId = entityId($(this));
            swal({
                title: "Are you sure?",
                text: "You will not be able to recover this article!",
                type: "warning",
                showCancelButton: true,
                confirmButtonClass: "btn-danger",
                confirmButtonText: "Yes, delete it!",
                cancelButtonText: "No, cancel!",
                closeOnConfirm: false,
                closeOnCancel: false,
                showLoaderOnConfirm: true
            }, function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        type: 'POST',
                        url: 'personal-blog',
                        data: {deleteArticle: articleId},
                        cache: false,
                        beforeSend: () => {
                        },
                        success: (resp) => {
                            swal("Deleted!", "Your article has been deleted.", "success");
                        },
                        error: (msg, status) => {
                            console.log("error of deleting article!!!");
                            console.log(status);
                            console.log(msg);
                        },
                        complete: () => {
                        }
                    });
                } else {
                    swal("Cancelled", "Your article is safe :)", "error");
                }
            });

        });
    }


    /**
     --   ██████╗ ██████╗ ███╗   ███╗███╗   ███╗███████╗███╗   ██╗████████╗
     --  ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔════╝████╗  ██║╚══██╔══╝
     --  ██║     ██║   ██║██╔████╔██║██╔████╔██║█████╗  ██╔██╗ ██║   ██║
     --  ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║
     --  ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║███████╗██║ ╚████║   ██║
     --   ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝
     */
    /**
     * AJAX load comments tree of article
     */
    $(document).on("click", ".show-comment-btn", function () {
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
                data: {showCommentsOfArticle: articleID},
                cache: false,
                beforeSend: function () {
                    loadingImg.css("display", "block");
                    arrow.removeClass("fa-chevron-down");
                    arrow.addClass("fa-spinner fa-pulse fa-fw");
                },
                success: function (resp) {
                    loadingImg.css("display", "none");
                    commentArea.empty();
                    // Calculate number of comments, pass by ref
                    const numComments = {num: 0};
                    resp.forEach(comments => showCascadingComments(comments, commentArea, numComments));
                    $("#num-comments-"+articleID).html(numComments.num);

                    arrow.removeClass("fa-spinner fa-pulse fa-fw");
                    arrow.addClass("fa-chevron-up");
                    commentArea.slideDown();
                },
                error: (msg, status) => {
                    console.log("show-comment-btn error!!!");
                    console.log(status);
                    console.log(msg);
                },
                complete: () => {
                    // event binding for ajax loaded area
                    if (!commentArea.hasClass("clickable-active")){
                        commentActions(articleID);
                        commentArea.addClass("clickable-active");
                    }
                }
            });

        } else {
            commentArea.slideUp();
            arrow.removeClass("fa-chevron-up");
            arrow.addClass("fa-chevron-down");
        }
    });

    /**
     * Leave comment under articles
     */
    $(document).on("click", ".leave-comment-submit", function (e) {
        e.preventDefault();
        const articleId = entityId($(e.target));
        const $commentText = $("#leave-comment-text-" + articleId);

        if($commentText.val()==="" || $commentText.val()===undefined){
            swal("Write something!", "You need to have something to say!", "warning");
            return;
        }

        $.ajax({
            type: 'POST',
            url: 'personal-blog',
            data: {
                replyArticle: articleId,
                commenter: loggedInUser,
                content: $commentText.val(),
            },
            cache: false,
            beforeSend: function () {
                swal({
                    title: "Wait a second...",
                    text: "Ajax request running",
                    type: "info",
                    showLoaderOnConfirm: true
                });
                $("#showCommentBtn-"+articleId).click();
            },
            success: function (resp) {
                $("#leave-comment-text-" + articleId).val("");
                swal("Congrats!", "Your comment is posted.", "success");

                /*upload file for newly created comment*/
                const uploadCommentId = resp.startsWith("inserted") ? resp.split(" ")[1] : articleId;

                //change the uploadArea form $div action parameter to newly created articleId
                $("#uploadForm-"+articleId).attr("action", "/File-Upload?commentId="+uploadCommentId);
                $("#uploadButton-comment-"+articleId).click();

            },
            error: (msg, status) => {
                console.log("error!!!");
                console.log(status);
                console.log(msg);
            },
            complete: () => {
                $("#showCommentBtn-"+articleId).click();
            }
        })
    });

    function commentActions(articleID) {
        const $commentArea = $("#comment-area-"+articleID);

        /**
         * show reply comment form
         */
        $commentArea.on("click", ".reply-comment-btn", function (e) {
            e.preventDefault();
            const articleId = entityId($(e.delegateTarget));
            const cmtId = entityId($(this));
            const replyForm = $("#popup-reply-" + cmtId);
            replyForm.slideDown();
            $(".close").on("click", function () {
                replyForm.slideUp();
            })
        });

        /**
         * show edit comment form
         */
        $commentArea.on("click", ".edit-comment-btn", function (e) {
            e.preventDefault();
            const articleId = entityId($(e.delegateTarget));
            const cmtId = entityId($(this));
            const editForm = $("#popup-edit-" + cmtId);
            editForm.slideDown();
            $(".close").on("click", function () {
                editForm.slideUp();
            })
        });

        /**
         * reply a comment
         */
        $commentArea.on("click", ".reply-submit", function (e) {
            e.preventDefault();
            const articleId = entityId($(e.delegateTarget));
            const cmtId = entityId($(e.target));
            const $replyText = $("#reply-text-" + cmtId);

            if($replyText.val()==="" || $replyText.val()===undefined){
                swal("Write something!", "You need to have something to say!", "warning");
                return;
            }

            $.ajax({
                type: 'POST',
                url: 'personal-blog',
                data: {
                    replyArticle: articleId,
                    replyComment: cmtId,
                    commenter: loggedInUser,
                    content: $replyText.val(),
                },
                cache: false,
                beforeSend: function () {
                    swal({
                        title: "Wait a second...",
                        text: "Ajax request running",
                        type: "info",
                        showLoaderOnConfirm: true
                    });
                    $("#showCommentBtn-"+articleId).click();
                },
                success: function (resp) {
                    swal("Congrats!", "Your comment is posted.", "success");
                },
                error: (msg, status) => {
                    console.log("error!!!");
                    console.log(status);
                    console.log(msg);
                },
                complete: () => {
                    // $(this).parentNode.slideUp();
                    $("#showCommentBtn-"+articleId).click();
                }
            })
        });

        /**
         * edit a comment
         */
        $commentArea.on("click", ".edit-submit", function (e) {
            e.preventDefault();
            const articleId = entityId($(e.delegateTarget));
            const cmtId = entityId($(e.target));
            const $editText = $("#edit-text-" + cmtId);

            if($editText.val()==="" || $editText.val()===undefined){
                swal("Write something!", "You need to have something to say!", "warning");
                return;
            }

            $.ajax({
                type: 'POST',
                url: 'personal-blog',
                data: {
                    editComment: cmtId,
                    content: $editText.val()
                },
                cache: false,
                beforeSend: function () {
                    swal({
                        title: "Wait a second...",
                        text: "Ajax request running",
                        type: "info",
                        showLoaderOnConfirm: true
                    });
                    $("#showCommentBtn-"+articleId).click();
                },
                success: function () {
                    swal("Congrats!", "Your comment is updated.", "success");
                },
                error: (msg, status) => {
                    console.log("error editing comment!!!");
                    console.log(status);
                    console.log(msg);
                },
                complete: () => {
                    // $(this).parentNode.slideUp();
                    $("#showCommentBtn-"+articleId).click();
                }

            })
        });

        /**
         * delete a comment
         */
        $commentArea.on("click", ".delete-comment-btn", function (e) {
            e.preventDefault();
            const articleId = entityId($(e.delegateTarget));
            const cmtId = entityId($(this));

            swal({
                    title: "Are you sure?",
                    text: "Your will not be able to recover this comment!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonClass: "btn-danger",
                    confirmButtonText: "Yes, delete it!",
                    closeOnConfirm: false,
                    showLoaderOnConfirm: true
                },
                function () {

                    $.ajax({
                        type: 'POST',
                        url: 'personal-blog',
                        data: {deleteComment: cmtId},
                        cache: false,
                        beforeSend: () => {
                            $("#showCommentBtn-"+articleId).click();
                        },
                        success: () => {
                            swal("Deleted!", "This comment has been deleted.", "success");
                        },
                        error: (msg, status) => {
                            console.log("error of deleting comment!!!");
                            console.log(status);
                            console.log(msg);
                        },
                        complete: () => {
                            $("#showCommentBtn-"+articleId).click();
                        }
                    });
                });
        });
    }

});

