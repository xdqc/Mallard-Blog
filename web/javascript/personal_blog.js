"use strict";

/**
 * get the id of the entity of which a html element represents
 */
const entityId = e => e.attr("id").slice(e.attr("id").lastIndexOf("-") + 1);


/**
 * helper function recursively show comment tree
 */
const showCascadingComments = (commentTree, $parent) => {
    // To exploit var hijacking, use plain for loop rather than map, reduce, filter...
    for (let commentArr of commentTree) {
        if (!$.isArray(commentArr)) {
            for (let cmtId in commentArr) {
                // get a value of json without pre-knowing its key
                if (commentArr.hasOwnProperty(cmtId)) {
                    const comment = commentArr[cmtId];
                    const ago = $.timeago(Date.parse(comment["createTime"]));
                    const $dl = $("<dl class='comment'>").appendTo($parent)
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

                    const deleteBtn = $("<a class='delete-comment-btn fa fa-pencil-square-o'>")
                        .attr("id", "delete-comment-btn-" + cmtId)
                        //.attr("href", "#popup-delete-" + cmtId)
                        .text(" delete");


                    /*


                     */

                    //This is a special use case of exploiting of var hijacking to access it outside loop
                    var $pp = ($("<dd class='comment'>").text(comment.content)).appendTo($dl);

                    if (loggedInUser !== 0) {
                        $pp.append(replyBtn);
                    }
                    if (loggedInUser === comment["commenterId"]) {
                        $pp.append(editBtn);
                    }
                    if (loggedInUser === comment["articleAuthorId"] || loggedInUser === comment["commenterId"]) {
                        $pp.append(deleteBtn);
                    }
                    if (loggedInUser !== 0) {
                        $pp.append(replyForm);
                    }
                    if (loggedInUser === comment["commenterId"]) {
                        $pp.append(editForm);
                    }

                }
            }
        } else {
            //The first elem in commentArr will ALWAYS be a commentObj, so $pp will be initialized
            showCascadingComments(commentArr, $pp);
        }
    }
};


$(document).ready(function () {

    /**
     * AJAX load comments of article
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
     * AJAX load article content
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
     * Ajax load edit article area
     */
    $(".edit-article-btn").on("click", function () {

        const articleId = entityId($(this));
        const editArea = $("#edit-article-area-"+articleId);
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
                createOrEditArticleHandler($(".accordion-bar"));
            }
        })
    });

    /**
     * Ajax create or edit article
     */
    $(".edit-article-area").on("change", ".publish-mode", function (e) {

        const articleId = entityId($(this));

        const form = articleActions(articleId);

        if (form["publishMode"][0].value === "publish") {
            form["datePicker"].hide();
            form["publishBtn"].empty();
            form["publishBtn"].append($("<span class='fa fa-paper-plane' aria-hidden='true'>").text(" Publish"));

        } else if (form["publishMode"][0].value === "draft") {
            form["datePicker"].show();
            form["publishBtn"].empty();
            form["publishBtn"].append($("<span class='fa fa-floppy-o' aria-hidden='true'>").text(" Save"));
            let date = new Date();
            date = moment(date).format("YYYY-MM-DDTkk:mm");
            form["datePicker"].val(date);
        }
    });

    $(".edit-article-area").on("click", ".publish", function (e) {
        e.preventDefault();
        const articleId = entityId($(this));

        const form = articleActions(articleId);

        const uploadingImg = $("img.uploading-img");

        if (form["title"].val()===""){
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

        if (form["content"].val()==="" || form["content"].val()===undefined){
            swal("Write something!", "You need have some content!", "warning");
            return;
        }

        let availableDate = new Date();

        if (form["publishMode"][0].value==="draft"){
            if (form["datePicker"].val()==="" || form["datePicker"].val()===undefined){
                alert("Available time is required.");
                return;
            }
            let date = form["datePicker"].val();
            date = moment(date).format("YYYY-MM-DDTkk:mm");
            availableDate = new Date(date);
        }

        const article = {};
        article["articleId"] = articleId>0?articleId:"0";
        article["title"] = form["title"].val();
        article["content"] = form["content"].val();
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
                if (resp==="inserted"){
                    msg = form["publishMode"][0].value==="publish" ? "Your article are published."
                        : "Your article will be visible to public on " + availableDate.toLocaleString();
                    swal("Congratulations ",msg,"success");
                } else if (resp==="updated") {
                    msg = form["publishMode"][0].value==="publish" ? "Your article are updated."
                        : "Your updated article will be visible to public on " + availableDate.toLocaleString();
                    swal("Congratulations ",msg,"success");
                } else {
                    msg = "Server error";
                    swal("Oops ", msg, "danger")
                }


                console.log(resp);
            },
            error: (msg, status) => {
                console.log("error!!");
                console.log(status);
                console.log(msg);
                swal("Oops ", msg, "danger")
            },
            complete: () => {
                uploadingImg.hide();
            }

        })
    });




    /**
     * Functions to handle replying, editing and deleting comments
     */
    function commentActions() {
        $(".reply-comment-btn").on("click", function (e) {
            e.preventDefault();
            console.log($(this));
            const cmtId = entityId($(this));
            const replyForm = $("#popup-reply-" + cmtId);
            replyForm.slideDown();
            $(".close").on("click", function () {
                replyForm.slideUp();
            })
        });

        $(".edit-comment-btn").on("click", function (e) {
            e.preventDefault();
            const cmtId = entityId($(this));
            const editForm = $("#popup-edit-" + cmtId);
            editForm.slideDown();
            $(".close").on("click", function () {
                editForm.slideUp();
            })
        });

        $(".delete-comment-btn").on("click", function (e) {
            e.preventDefault();
            const cmtId = entityId($(this));

            swal({
                    title: "Are you sure?",
                    text: "Your will not be able to recover this comment!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonClass: "btn-danger",
                    confirmButtonText: "Yes, delete it!",
                    closeOnConfirm: false
                },
                function(){
                    //TODO delete the comment
                    swal("Deleted!", "This comment has been deleted.", "success");
                });

        });

        //TODO reply, edit comments ajax

    }




    /**
     * Functions to handle creating and editing articles
     */
    const articleActions = (id) => {
        console.log(id + " is working on?");

        const datePicker = $("#publish-time-"+id);
        const publishBtn = $("#publish-"+id);
        const publishMode = $("#publish-mode-"+id);
        const title = $("#input-article-title-"+id);
        const content = $("#input-article-content-"+id);

        //WYSIWYG
        title.attr("value", $("#article-title-"+id).text());    //don't val(), it will bind the value and wont change later
        content.text($("#article-content-"+id).text());

        return {
            "datePicker" : datePicker,
            "publishBtn" : publishBtn,
            "publishMode" : publishMode,
            "title" : title,
            "content" : content
        }
    };



    /**
     * let article actions 'focus on' creating new article, or editing one existing article
     */
    const createOrEditArticleHandler = (trigger) => {trigger.on("click", function () {
        const articleId = entityId($(this));
        $(".panel-collapse").collapse('hide');
        return articleActions(articleId);
    })};
    createOrEditArticleHandler($(".accordion-bar"));
});

