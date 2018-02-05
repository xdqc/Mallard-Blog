const getEntityId = (e) => $(e).attr("id").slice($(e).attr("id").lastIndexOf("-")+1, $(e).attr("id").length);
const getEntityParameterName = (e) => $(e).attr("id").split("-")[$(e).attr("id").split("-").length-2];
const isUserCheck = (e) => $(e).attr("id").split("-")[$(e).attr("id").split("-").length-3];
//add a input button to upload area
function addFileInput(attachmentId,parameterName) {
    var files = document.getElementById('files-' + parameterName + '-' +attachmentId);
    var newButtom = document.createElement("input");
    newButtom.setAttribute("type", "file");
    newButtom.setAttribute("name", "file");
    files.prepend(newButtom);
    // files.appendChild(document.createElement("br"));
}

//close the window make it look like in the same page
function goBack() {
    history.back();
}

//close the window make it look like in the same page
function showArticles(parameterName,value) {
    alert(parameterName + value);
    //deal with the multimedia gallery show content
    $('#showMultimedia-' + parameterName +'-' + value).click(function() {
        $.ajax({
            url : 'multimedia-gallery',
            data : {
                articleId : value
            },
            success : function(responseText) {
                $('multimediaShowAreatest').html(responseText);
                $('#multimediaShowArea-' + parameterName +'-' + value).html(responseText);
            }
        });
    });
}

$(document).ready(function () {

    //deal with the multimedia gallery show content
    //also works on ajax loaded part
    $(document).on("click", ".show-media", function() {
        const entityId = getEntityId($(this));
        const parameterName = getEntityParameterName($(this));
        const userCheck = isUserCheck($(this));
        alert(userCheck);
        if(userCheck.startsWith("UserCheck")){
           const userID = userCheck.split("_")[1];
            $.ajax({
                url : 'multimedia-gallery',
                data : {
                    entityId : entityId,
                    parameterName : parameterName,
                    userID : userID
                },
                success : function(responseText) {
                    $('#multimediaShowArea-' + parameterName + '-'+entityId).html(responseText);
                }
            });
        }else if(userCheck.toLowerCase() == "filelist" ){
            $.ajax({
                url : 'File-Manage',
                data : {
                    entityId : entityId,
                    parameterName : parameterName,
                    operateName : userCheck
                },
                success : function(responseText) {
                    $('#multimediaShowArea-' + parameterName + '-'+entityId).html(responseText);
                }
            });
        }else if(userCheck.toLowerCase() == "delete" ){
            $.ajax({
                url : 'File-Manage',
                data : {
                    entityId : entityId,
                    parameterName : parameterName,
                    operateName : userCheck
                },
                success : function(responseText) {
                    $('#multimediaShowArea-' + parameterName + '-'+entityId).html(responseText);
                }
            });
        }else if(userCheck.toLowerCase() == "showmultimedia" ){
        $.ajax({
            url : 'multimedia-gallery',
            data : {
                entityId : entityId,
                parameterName : parameterName
            },
            success : function(responseText) {
                $('#multimediaShowArea-' + parameterName + '-'+entityId).html(responseText);
            }
        });
        }else if(userCheck.toLowerCase() == "activatelist" ){
            $.ajax({
                url: 'Attachment-Manage',
                data: {
                    entityId: entityId,
                    parameterName: parameterName,
                    operateName : userCheck
                },
                success: function (responseText) {
                    $('#multimediaShowArea-' + parameterName + '-' + entityId).html(responseText);
                }
            });
        }
    });

    //deal with the upload file field
    // $(document).on("submit", '.uploadForm', function(e) {
    //     e.preventDefault();
    // });
    $(document).on("click", '.uploadButton', function(e) {
        console.log(e);
        $.ajax({
            url : 'File-Upload',
            data : {
                articleId : $('#articleId').val()
            },
            success : function(responseText) {
                $('#uploadFileArea').html(responseText);
            }
        });
    });


    // $(document).on("click", '.uploadButton', function() {
    //     $.ajax({
    //         url : 'File-Upload',
    //         data : {
    //             articleId : $('#articleId').val()
    //         },
    //         success : function(responseText) {
    //             $('#uploadFileArea').html(responseText);
    //         }
    //     });
    // });

});

