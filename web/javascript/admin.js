$(document).ready(function () {
    /**
     * Send pw reset email to user's email address
     */
    $(".send-email").on("click", function () {
        const userId = entityId($(this));
        const username = $("#username-"+userId).html();
        const email = $("#email-address-"+userId).html();
        console.log(email);
        $.ajax({
            type: 'POST',
            url: 'admin',
            data: {
                sendEmail: email,
                userId: userId,
                username: username
            },
            cache: false,
            beforeSend: () => {
            },
            success: (resp) => {
                swal("Cheers!", resp, "success");
            },
            error: (msg, status) => {
                console.log("error of sending email!!!");
                console.log(status);
                console.log(msg);
            },
            complete: () => {
            }
        })
    });

    /**
     * Delete user action
     */
    $(".delete-user").on("click", function () {
        const userId = entityId($(this));
        $.ajax({
            type: 'POST',
            url: 'admin',
            data: {
                deleteUser: userId,
            },
            cache: false,
            beforeSend: () => {
            },
            success: (resp) => {
                swal("Cheers!", resp, "success");
            },
            error: (msg, status) => {
                console.log("error of deleting user!!!");
                console.log(status);
                console.log(msg);
            },
            complete: () => {
            }
        })
    });

    /**
     * Recover user action
     */
    $(".recover-user").on("click", function () {
        const userId = entityId($(this));
        $.ajax({
            type: 'POST',
            url: 'admin',
            data: {
                recoverUser: userId,
            },
            cache: false,
            beforeSend: () => {
            },
            success: (resp) => {
                swal("Cheers!", resp, "success");
            },
            error: (msg, status) => {
                console.log("error of recover user!!!");
                console.log(status);
                console.log(msg);
            },
            complete: () => {
            }
        })
    });

    /**
     * Show article action
     */
    $(".show-article").on("click", function () {
        const articleId = entityId($(this));
        $.ajax({
            type: 'POST',
            url: 'admin',
            data: {
                showArticle: articleId,
            },
            cache: false,
            beforeSend: () => {
            },
            success: (resp) => {
                swal("Cheers!", resp, "success");
            },
            error: (msg, status) => {
                console.log("error of show article!!!");
                console.log(status);
                console.log(msg);
            },
            complete: () => {
            }
        })
    });

    /**
     * Hide article action
     */
    $(".hide-article").on("click", function () {
        const articleId = entityId($(this));
        $.ajax({
            type: 'POST',
            url: 'admin',
            data: {
                hideArticle: articleId,
            },
            cache: false,
            beforeSend: () => {
            },
            success: (resp) => {
                swal("Cheers!", resp, "success");
            },
            error: (msg, status) => {
                console.log("error of hide article!!!");
                console.log(status);
                console.log(msg);
            },
            complete: () => {
            }
        })
    });


    /**
     * Show comment action
     */
    $(".show-comment").on("click", function () {
        const commentId = entityId($(this));
        $.ajax({
            type: 'POST',
            url: 'admin',
            data: {
                showComment: commentId,
            },
            cache: false,
            beforeSend: () => {
            },
            success: (resp) => {
                swal("Cheers!", resp, "success");
            },
            error: (msg, status) => {
                console.log("error of show comment!!!");
                console.log(status);
                console.log(msg);
            },
            complete: () => {
            }
        })
    });

    /**
     * Hide article action
     */
    $(".hide-comment").on("click", function () {
        const commentId = entityId($(this));
        $.ajax({
            type: 'POST',
            url: 'admin',
            data: {
                hideComment: commentId,
            },
            cache: false,
            beforeSend: () => {
            },
            success: (resp) => {
                swal("Cheers!", resp, "success");
            },
            error: (msg, status) => {
                console.log("error of hide comment!!!");
                console.log(status);
                console.log(msg);
            },
            complete: () => {
            }
        })
    });


    /**
     * add new user by admin
     */
    $("#add-new-user-admin").on("click", function () {
        $("#user-table-body").append(

        )

    });

});