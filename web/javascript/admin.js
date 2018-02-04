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


});