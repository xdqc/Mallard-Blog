<div class="panel-group" id="accordion">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"><span
                        class="glyphicon glyphicon-file">
                            </span>POST NEW ARTICLE</a>
            </h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse in">
            <div class="panel-body">
                <div class="container"></div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <input type="text" class="form-control title" placeholder="Title" required/>
                        </div>
                        <div class="form-group">
                            <textarea class="form-control content" placeholder="Content" rows="5" required></textarea>
                        </div>
                        <img src="pictures/uploading.gif" alt="uploading..." class="uploading-img">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="well well-sm well-primary">
                            <div class="input-group image-preview">
                                <a target="_blank" href="/File-Upload?articleId=${blog.getArticle().getId()}" class="btn btn-primary">Upload
                                    multimedia</a>
                            </div><!-- /input-group image-preview [TO HERE]-->
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="well well-sm well-primary">
                            <form class="form form-inline " role="form">
                                <div class="form-group">
                                    <button type="submit" class="btn btn-success publish"
                                            id="publish-${sessionScope.get("loggedInUser").getId()}">
                                        <span class="fa fa-paper-plane"> Publish</span>
                                    </button>
                                    <button type="button" class="btn btn-default preview"
                                            id="preview-${sessionScope.get("loggedInUser").getId()}">
                                        <span class="fa fa-eye"> Preview</span>
                                    </button>
                                </div>
                                <div class="form-group">
                                    <select class="form-control text-input-dialog publish-mode"
                                            id="publish-mode-${sessionScope.get("loggedInUser").getId()}">
                                        <option value="publish">Publish</option>
                                        <option value="draft">Draft</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <input type="datetime-local" class="form-control publish-time"
                                           id="publish-time-${sessionScope.get("loggedInUser").getId()}" value=""
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







