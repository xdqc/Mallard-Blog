<div class="leave-comment" id="leave-comment-${blog.getArticle().getId()}">
    <h4>Leave a comment</h4>
    <div class="widget-area no-padding blank">
        <div class="status-upload">
            <form>
                <textarea id="leave-comment-text-${blog.getArticle().getId()}"
                          placeholder="What are you thinking about this article?"></textarea>
                <ul class="list-unstyled list-inline">
                    <li><a title="" data-toggle="tooltip" data-placement="bottom"
                           data-original-title="Audio"><i class="fa fa-music"></i></a></li>
                    <li><a title="" data-toggle="tooltip" data-placement="bottom"
                           data-original-title="Video"><i
                            class="fa fa-video-camera"></i></a></li>
                    <li><a title="" data-toggle="tooltip" data-placement="bottom"
                           data-original-title="Sound Record"><i
                            class="fa fa-microphone"></i></a></li>
                    <li><a title="" data-toggle="tooltip" data-placement="bottom"
                           data-original-title="Picture"><i class="fa fa-picture-o"></i></a></li>
                </ul>
                <button type="submit" class="btn btn-success leave-comment-submit"
                        id="leave-comment-submit-${blog.getArticle().getId()}"><i
                        class="fa fa-share"></i> Comment
                </button>
            </form>
        </div>
    </div>
</div>
