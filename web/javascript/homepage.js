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


    $("#load-homepage-user-profile").click();
    //load some articles when page ready
    for(let i=0; i<5; i++){
        (function() {
            setTimeout(function() {
                $("#load-more-articles").click();
            }, i * 1200);
        })(i);
    }
    setTimeout(function () {
        $("#load-more-articles").css("display","block");
    }, 5000);

});

