<div class="leave-comment" id="leave-comment-${blog.getArticle().getId()}">
    <h4 class="leave-a-comment" style="display: none">Comment</h4>
    <div class="widget-area no-padding blank">
        <div class="status-upload">
            <form>
                <textarea id="leave-comment-text-${blog.getArticle().getId()}"
                          placeholder="What are you thinking about this article?"></textarea>
                <ul class="list-unstyled list-inline">
                    <li><a title="" data-toggle="collapse" href="#uploadArea-${blog.getArticle().getId()}">
                        <i class="fa fa-video-camera"></i></a></li>
                    <li><a title="" data-toggle="collapse" href="#uploadArea-${blog.getArticle().getId()}">
                        <i class="fa fa-picture-o"></i></a></li>
                </ul>
                <button type="submit" class="btn btn-success leave-comment-submit"
                        id="leave-comment-submit-${blog.getArticle().getId()}"><i
                        class="fa fa-share"></i> Comment
                </button>
            </form>

            <!-- collapse style upload file begin-->

            <div id="uploadArea-${blog.getArticle().getId()}" class="collapse upload-area">
                <form class="uploadForm" id="uploadForm-${blog.getArticle().getId()}" action="/File-Upload?commentId=0" method="post" enctype="multipart/form-data">
                    <fieldset id="files-commentToArticle-${blog.getArticle().getId()}">
                        <legend>Select your file</legend>
                        <input id ="file" type="file" name="file">
                        <input type="button" value="Add more files" onclick="addFileInput('${blog.getArticle().getId()}','commentToArticle')"><br>
                    </fieldset>
                    <input id="uploadButton-comment-${blog.getArticle().getId()}" class="upload-buttons" type = "submit" value = "Upload" style="display: none">
                </form>
                <div id="uploadedFilesArea"></div>
            </div>
            <!-- collapse style upload file end-->
        </div>
    </div>
</div>
