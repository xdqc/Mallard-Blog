<div class="panel-group" id="accordion-${articleId}">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a class="accordion-bar" id="accordion-bar-${articleId}" data-toggle="collapse" data-parent="#accordion"
                    href="#collapse-${articleId}"><span class="glyphicon glyphicon-file"></span>
                    ${articleId>0? "Edit this article":"POST NEW ARTICLE"}
                </a>
            </h4>
        </div>
        <div id="collapse-${articleId}" class="panel-collapse collapse">
            <div class="panel-body">
                <div class="container"></div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <input type="text" class="form-control title" id="input-article-title-${articleId}"
                                   placeholder="Title" required/>
                        </div>
                        <div class="form-group">
                            <textarea class="form-control content" id="input-article-content-${articleId}"
                                      placeholder="Content" rows="5" required></textarea>
                        </div>
                        <img src="pictures/uploading.gif" alt="uploading..." class="uploading-img">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="well well-sm well-primary">
                            <div class="input-group image-preview">

                                <!-- collapse style upload file begin-->
                                <div>
                                    <script type="text/javascript" src="../javascript/uploadFile.js"></script>
                                    <a id="uploadFileButton" data-toggle="collapse" href="#uploadArea-${articleId}" class="btn btn-primary">
                                        <span class="glyphicon glyphicon-file"></span>Upload multimedia</a>
                                    <div id="uploadArea-${articleId}" class="collapse">
                                        <form id="uploadForm" action="/File-Upload?articleId=${articleId}" method="post" enctype="multipart/form-data">
                                            <fieldset id="files-article-${articleId}">
                                                <legend>Select your file</legend>
                                                <input id ="file" type="file" name="file" /><input type="button" value="Add more files" onclick="addFileInput('${articleId}','article')"><br>
                                            </fieldset>
                                            <input id="uploadButton-article-${articleId}" class="upload-buttons" type = "submit" value = "Upload">
                                        </form>
                                        <div id="uploadedFilesArea"></div>
                                    </div>
                                </div>
                                <!-- collapse style upload file end-->


                            </div><!-- /input-group image-preview [TO HERE]-->
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="well well-sm well-primary">
                            <form class="form form-inline " role="form">
                                <div class="form-group">
                                    <button type="submit" class="btn btn-success publish"
                                            id="publish-${articleId}">
                                        <span class="fa fa-paper-plane"> Publish</span>
                                    </button>
                                    <button type="button" class="btn btn-default preview"
                                            id="preview-${articleId}">
                                        <span class="fa fa-eye"> Preview</span>
                                    </button>
                                </div>
                                <div class="form-group">
                                    <select class="form-control text-input-dialog publish-mode"
                                            id="publish-mode-${articleId}">
                                        <option value="publish">Publish</option>
                                        <option value="draft">Draft</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <input type="datetime-local" class="form-control publish-time"
                                           id="publish-time-${articleId}" value=""
                                           placeholder="Date" style="display: none" required/>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>







