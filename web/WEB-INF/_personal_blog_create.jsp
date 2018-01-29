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
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Title" required/>
                        </div>
                        <div class="form-group">
                            <textarea class="form-control" placeholder="Content" rows="5" required></textarea>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="well well-sm well-primary">
                            <div class="input-group image-preview">
                                <input type="text" class="form-control image-preview-filename"
                                       disabled="disabled">
                                <!-- don't give a name === doesn't send on POST/GET -->
                                <div class="input-group-btn">
                                    <!-- image-preview-clear button -->
                                    <button type="button" class="btn btn-default image-preview-clear"
                                            style="display:none;">
                                        <span class="glyphicon glyphicon-remove"></span> Clear
                                    </button>
                                    <!-- image-preview-input -->
                                    <div class="btn btn-default image-preview-input">
                                        <span class="glyphicon glyphicon-folder-open"></span>
                                        <span class="image-preview-input-title">Browse</span>
                                        <input type="file" accept="image/png, image/jpeg, image/gif"
                                               name="input-file-preview"/>
                                        <!-- rename it -->
                                    </div>
                                </div>
                            </div><!-- /input-group image-preview [TO HERE]-->
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="well well-sm well-primary">
                            <form class="form form-inline " role="form">
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
                                <div class="form-group">
                                    <button type="submit" class="btn btn-success btn-sm publish" id="publish-${sessionScope.get("loggedInUser").getId()}">
                                        <span class="fa fa-floppy-o"></span> Publish
                                    </button>
                                    <button type="button" class="btn btn-default btn-sm preview" id="preview-${sessionScope.get("loggedInUser").getId()}">
                                        <span class="fa fa-eye"></span> Preview
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {

        $("select.publish-mode").on("change", function() {
            const datePicker = $("input.publish-time");
            const publishBtn = $("button.publish");
            if (this.value==="publish"){
                datePicker.hide();
                publishBtn.text("Publish");
                publishBtn.children().removeClass("fa-floppy-o");
                publishBtn.children().addClass("fa-eye");
            } else if (this.value==="draft"){
                datePicker.show();
                publishBtn.text("Save");
                publishBtn.children().removeClass("fa-eye");
                publishBtn.children().addClass("fa-floppy-o");
            }
        })

    })
</script>