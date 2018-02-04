//const entityId = (e) => $(e).attr("id").slice($(e).attr("id").lastIndexOf("-")+1, $(e).attr("id").length);
const entityParameterName = (e) => $(e).attr("id").split("-")[$(e).attr("id").split("-").length-2];
const isUserCheck = (e) => $(e).attr("id").split("-")[$(e).attr("id").split("-").length-3];
//add a input button to upload area
function addFileInput(attachmentId,parameterName) {
    var files = document.getElementById('files-' + parameterName + '-' +attachmentId);
    var newButtom = document.createElement("input");
    newButtom.setAttribute("type", "file");
    newButtom.setAttribute("name", "file");
    files.appendChild(newButtom);
    files.appendChild(document.createElement("br"));
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
    $('.show-media').click(function() {
        const attachmentId = entityId($(this));
        const parameterName = entityParameterName($(this));
        const userCheck = isUserCheck($(this));
        if(userCheck.startsWith("UserCheck")){
           const userID = userCheck.split("_")[1];
            alert(userID);
            $.ajax({
                url : 'multimedia-gallery',
                data : {
                    attachmentId : attachmentId,
                    parameterName : parameterName,
                    userID : userID
                },
                success : function(responseText) {
                    $('#multimediaShowArea-' + parameterName + '-'+attachmentId).html(responseText);
                }
            });
        }else if(userCheck.toLowerCase() == "filelist" ){
            $.ajax({
                url : 'File-Manage',
                data : {
                    attachmentId : attachmentId,
                    parameterName : parameterName,
                    operateName : userCheck
                },
                success : function(responseText) {
                    $('#multimediaShowArea-' + parameterName + '-'+attachmentId).html(responseText);
                }
            });
        }else if(userCheck.toLowerCase() == "delete" ){
            $.ajax({
                url : 'File-Manage',
                data : {
                    attachmentId : attachmentId,
                    parameterName : parameterName,
                    operateName : userCheck
                },
                success : function(responseText) {
                    $('#multimediaShowArea-' + parameterName + '-'+attachmentId).html(responseText);
                }
            });
        }else if(userCheck ==null || userCheck.toLowerCase() == "" ){
        $.ajax({
            url : 'multimedia-gallery',
            data : {
                attachmentId : attachmentId,
                parameterName : parameterName
            },
            success : function(responseText) {
                $('#multimediaShowArea-' + parameterName + '-'+attachmentId).html(responseText);
            }
        });
        }
    });

    //deal with the upload file field
    $('#uploadButton').click(function() {
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
})

