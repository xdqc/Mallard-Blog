"use strict";

$(document).ready(function () {
    $("#load-homepage-user-profile").on("click", function () {
        const profileArea = $("#profile-area");
        $.ajax({
            type: 'post',
            url: 'home-page',
            data: {
                loadHomepageProfile: loggedInUser,
            },
            cache: false,
            beforeSend: () => {
            },
            success: (resp) => {
                profileArea.html(resp);
            },
            error: (msg, status) => {
                console.log("error of loading homepage user profile!!!");
                console.log(status);
                console.log(msg);
            },
            complete: () => {
                $("#load-article-img").hide();
            }
        })
    });

    $(".delete-account-btn").on("click", function (e) {
        e.preventDefault();

        const userId = entityId($(this));
        swal({
            title: "Are you sure?",
            text: "You may not be able to recover your account!",
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
                    url: 'admin',
                    data: {deleteUser: userId},
                    cache: false,
                    beforeSend: () => {
                    },
                    success: (resp) => {
                        swal("Deleted!", "Your account has been deleted.", "success");
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
                swal("Cancelled", "Your account is safe :)", "error");
            }
        });
    });



    //load user profile panel when user logged in
    $("#load-homepage-user-profile").click();
    //load some articles when page ready
    for(let i=0; i<5; i++){
        (function() {
            setTimeout(function() {
                $("#load-more-articles").click();
            }, i * 1200);
        })(i);
    }
    //show load more btn after 5 sec
    setTimeout(function () {
        $("#load-more-articles").css("display","block");
    }, 5000);



});

