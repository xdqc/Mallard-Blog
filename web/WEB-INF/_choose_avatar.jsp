<%--
  Created by IntelliJ IDEA.
  User: nr56
  Date: 4/02/2018
  Time: 4:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="choose-avatar-container">
    <form action="sign-up?setupAvatarFor=${newUserId}" id="choose-avatar">
    <div class="form-group">
        <h3>Choose Your profile Image:</h3>
        <br><br>
        <div id="selected-pic" style="margin-left: 3em"><img id="defaultimg" src="../pictures/avatars/default.jpg" alt="default" width="220"/></div>

            <div id="thumbview">
                <div class="thumb-holder"><label for="avatar-1"><img src="pictures/avatars/1.jpg" alt="1" width="100"></label>
                    <input type="radio" value="1" name="avatar" id="avatar-1">
                </div>
                <div class="thumb-holder"><label for="avatar-2"><img src="pictures/avatars/2.png" alt="2" width="100"></label>
                    <input type="radio" value="2" name="avatar" id="avatar-2">
                </div>
                <div class="thumb-holder"><label for="avatar-3"><img src="pictures/avatars/3.png" alt="3" width="100"></label>
                    <input type="radio" value="3" name="avatar" id="avatar-3">
                </div>
                <div class="thumb-holder"><label for="avatar-4"><img src="pictures/avatars/4.png" alt="4" width="100"></label>
                    <input type="radio" value="4" name="avatar" id="avatar-4">
                </div>
                <div class="thumb-holder"><label for="avatar-5"><img src="pictures/avatars/5.png" alt="5" width="100"></label>
                    <input type="radio" value="5" name="avatar" id="avatar-5">
                </div>
                <div class="thumb-holder"><label for="avatar-6"><img src="pictures/avatars/6.png" alt="6" width="100"></label>
                    <input type="radio" value="6" name="avatar" id="avatar-6">
                </div>
                <div class="thumb-holder"><label for="avatar-7"><img src="pictures/avatars/7.png" alt="7" width="100"></label>
                    <input type="radio" value="7" name="avatar" id="avatar-7">
                </div>

        </div>
    </div>

        <br><br>
        <input type="submit" value="Confirm" id="choose-avatar-submit" class="btn btn-primary">
    </form>

    <div class="form-group" id="upload-avatar-form">
        <input type="radio" value="9" name="avatar" id="avatar-9">
        <label for="avatar-9">Choose a photo from your device</label>
        <br><br>
        <!-- collapse style upload file begin-->
        <div>
            <a id="uploadFileButton" data-toggle="collapse" href="#uploadArea" class="btn btn-primary">
                <span class="glyphicon glyphicon-file"></span>Upload multimedia</a>
        </div>
        <div id="uploadArea" class="collapse upload-area">
            <form id="uploadForm" action="File-Upload?userId=${newUserId}" method="post" enctype="multipart/form-data" class="uploadForm">
                <fieldset id="files-commentToArticle">
                    <legend>Select your file</legend>
                    <input id ="file" type="file" name="file" />
                </fieldset>
                <input id="uploadButton-comment-${newUserId}" class="upload-buttons" type = "submit" value = "Upload">
            </form>
            <div id="uploadedFilesArea"></div>
        </div>
        <!-- collapse style upload file end-->
    </div>

</div>

<script type="text/javascript">
    const loginPage = $('#login-page');

    $('form#choose-avatar').on('submit', function(event) {
        event.preventDefault(); // or return false, your choice
        console.log($(this).serialize());

        $.ajax({
            url: $(this).attr('action'),
            type: 'post',
            data: $(this).serialize(),
            success: function(resp, status) {
                // if success, HTML response is expected, so replace current
                if(resp === "success"){
                    swal("congratulations, you have avatar now!","Please sign up ...","success");
                    redirectToLogin();
                }
                else if (resp === "error"){
                    swal("SQL error");
                }
            },
            error: function (data, status) {
                swal("Sorry, there is an error on your form!");
            }

        });
    });


    function redirectToLogin() {
        $.ajax({
            url: 'login',
            type: 'post',
            data: 'login=newSignUp',
            success: function(resp, status) {
                $('#box').hide();
                $('#choose-avatar-container').hide();
                loginPage.html(resp);
                loginPage.slideDown(2200);
            },
            error: function (resp, status) {
                swal(status);
                console.log(resp);
            }

        });
    }
</script>

<style>
    #upload-avatar-form{
        position: relative;
        top: -500px;
        left: 350px;
        width: auto;
    }

</style>
