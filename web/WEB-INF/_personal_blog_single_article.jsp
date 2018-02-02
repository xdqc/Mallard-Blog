<article class="panel panel-info article-panel" id="article-panel-${blog.getArticle().getId()}">
    <div class="panel-heading">
        <h3 class="article-title"
            id="article-title-${blog.getArticle().getId()}">
            <a href="personal-blog?userId=${blog.getAuthor().getId()}#article-title-${blog.getArticle().getId()}">
                ${blog.getArticle().getTitle()}
            </a></h3>

    </div>
    <div class="panel-body">

        <div class="panel-text article-author"><a href="personal-blog?userId=${blog.getAuthor().getId()}">
            <span class="fa  fa-user"></span> ${blog.getAuthor().getFName()} ${blog.getAuthor().getLName()}</a>
        </div>
        <div class="panel-text article-time">
            <span class="h5 text-muted"><span class="fa fa-clock-o"></span>
            ${blog.getArticle().getCreateTime().toLocalDateTime()}&nbsp;&nbsp;&nbsp;</span>

        </div>
        <div class="panel-text article-likes">
            <span class="h5 text-muted"> ${blog.getArticle().getLikeNum()}&nbsp;<span
                    class="fa fa-thumbs-up"></span></span>
        </div>

        <img class="panel-img-top img-responsive" src="https://source.unsplash.com/random/${Math.round((Math.random()*600))+500}x${Math.round((Math.random()*200))+300}"
             alt="random picture"/>

        <%--&lt;%&ndash;display multimedia gallery here by ajax&ndash;%&gt;--%>
        <%--<div id="multimedia-gallery-${blog.getArticle().getId()}"></div>--%>

        <!-- collapse style multimedia gallery begin-->
        <div>
            <a id="showMultimedia-article-${blog.getArticle().getId()}" class="show-media" data-toggle="collapse" href="#multimediaShowArea-article-${blog.getArticle().getId()}">
                <span class="glyphicon glyphicon-file"></span>Show more multimedia</a>
            <div id="multimediaShowArea-article-${blog.getArticle().getId()}" class="collapse"></div>
        </div>
        <!-- collapse style multimedia gallery end-->

        <br>
        <div id="article-content-${blog.getArticle().getId()}" class="panel-text article-content">
            ${blog.getArticle().getContent().substring(0, Math.min(140, blog.getArticle().getContent().length()-1))}

            <img id="load-article-content-img-${blog.getArticle().getId()}" src="pictures/loading.gif"
                 alt="loading..."
                 width="45" style="display: none;" aria-hidden="true">

            <button id="read-more-${blog.getArticle().getId()}" class="btn btn-default btn-sm read-more-btn">
                Read more
            </button>
        </div>
        <br>
        <div class="edit-article-area" id="edit-article-area-${blog.getArticle().getId()}"></div>
        <br>

        <button type="button" id="showCommentBtn-${blog.getArticle().getId()}"
                class="btn btn-info show-comment-btn">
            <span class="badge" id="num-comments-${blog.getArticle().getId()}">${blog.getNumValidComments()}</span>
            Comments
            <span id="comment-arrow-${blog.getArticle().getId()}" class="fa fa-chevron-down"></span>
        </button>


        ${sessionScope.get("loggedInUser")==null? "" :
                '<div class="leave-comment" id="leave-comment-'.concat(id).concat('">    <h4 class="leave-a-comment" style="display: none">Comment</h4>    <div class="widget-area no-padding blank">        <div class="status-upload">            <form>                <textarea id="leave-comment-text-').concat(id).concat('"                          placeholder="What are you thinking about this article?"></textarea>                <ul class="list-unstyled list-inline">                    <li><a title="" data-toggle="collapse" href="#uploadArea-').concat(id).concat('">                        <i class="fa fa-video-camera"></i></a></li>                    <li><a title="" data-toggle="collapse" href="#uploadArea-').concat(id).concat('">                        <i class="fa fa-picture-o"></i></a></li>                </ul>                <button type="submit" class="btn btn-success leave-comment-submit"                        id="leave-comment-submit-').concat(id).concat('"><i class="fa fa-share"></i> Comment                </button>            </form>                        <div id="uploadArea-').concat(id).concat('" class="collapse">                <form id="uploadForm" action="/File-Upload?commentId=0" method="post" enctype="multipart/form-data">                    <fieldset id="files-commentToArticle-').concat(id).concat('">                        <legend>Select your file</legend>                        <input id ="file" type="file" name="file" /><input type="button" value="Add more files" onclick="addFileInput(').concat(id).concat(', ').concat("'commentToArticle'").concat(')"><br>                    </fieldset>                    <input id="uploadButton-comment-').concat(id).concat('" class="upload-buttons" type = "submit" value = "Upload"></form><div id="uploadedFilesArea"></div></div></div></div></div>')
        }

        <div id="comment-area-${blog.getArticle().getId()}" class="widget-area blank comment-area"
             style="display: none">
        </div>
    </div>
</article>
<br>
                <%--'<div class="leave-comment" id="leave-comment-'.concat(id).concat('"><h4 class="leave-a-comment" style="display:none">Comment</h4><div class="widget-area no-padding blank"><div class="status-upload"><form><textarea id="leave-comment-text-').concat(id).concat('"placeholder="What are you thinking about this article?"></textarea><ul class="list-unstyled list-inline"><li><a title="" data-toggle="tooltip" data-placement="bottom"data-original-title="Audio"><i class="fa fa-video-camera"></i></a><li><a title="" data-toggle="tooltip" data-placement="bottom"data-original-title="Picture"><i class="fa fa-picture-o"></i></a></li></ul><button type="submit" class="btn btn-success leave-comment-submit"id="leave-comment-submit-').concat(id).concat('"><iclass="fa fa-share"></i> Comment</button></form></div></div></div>')--%>
