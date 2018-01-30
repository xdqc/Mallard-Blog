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
                            <input type="text" class="form-control title" placeholder="Title" required/>
                        </div>
                        <div class="form-group">
                            <textarea class="form-control content" placeholder="Content" rows="5" required></textarea>
                        </div>
                        <img src="/pictures/uploading.gif" alt="uploading..." class="uploading-img">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="well well-sm well-primary">
                            <div class="input-group image-preview">
                                <%@include file="_upload_file.jsp" %>
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
                                    <button type="button" class="btn btn-success btn-sm publish"
                                            id="publish-${sessionScope.get("loggedInUser").getId()}">
                                        <span class="fa fa-paper-plane"></span> Publish
                                    </button>
                                    <button type="button" class="btn btn-default btn-sm preview"
                                            id="preview-${sessionScope.get("loggedInUser").getId()}">
                                        <span class="fa fa-eye"></span> Preview
                                    </button>
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

<style type="text/css">
    img.uploading-img {
        width: 100px;
        position: absolute;
        top: 30%;
        right: 45%;
        opacity: 0.618;
        display: none;
    }
</style>

<script type="text/javascript">

    const datePicker = $("input.publish-time");
    const publishBtn = $("button.publish");
    const publishMode = $("select.publish-mode");
    const uploadingImg = $("img.uploading-img");

    $(document).ready(function () {
        publishMode.on("change", function () {
            if (this.value === "publish") {
                datePicker.hide();
                publishBtn.empty();
                publishBtn.append($("<span class='fa fa-paper-plane' aria-hidden='true'>"));
                publishBtn.html(" Publish");

            } else if (this.value === "draft") {
                datePicker.show();
                publishBtn.empty();
                publishBtn.append($("<span class='fa fa-floppy-o' aria-hidden='true'>"));
                publishBtn.html(" Save");
            }
        });

        publishBtn.on("click", function () {
            datePicker.val(new Date());
            let availableDate = new Date();
            if (publishMode[0].value==="draft"){
                if (datePicker.val()===""){
                    alert("you have to choose a date and time");
                    return;
                }
                availableDate = new Date(datePicker.val());
            }

            const article = {};
            article["title"] = $("input.title").val();
            article["content"] = $("textarea.content").val();
            article["authorId"] = entityId($(this));
            article["createTime"] = new Date().getTime();
            article["validTime"] = availableDate.getTime();

            console.log(article);

            //Ajax post to servlet
            $.ajax({
                type: 'POST',
                url: 'personal-blog',
                data: {newArticle: JSON.stringify(article)},
                cache: false,
                beforeSend: () => {
                    uploadingImg.show();
                },

                success: resp => {
                    uploadingImg.hide();
                    $("input.title").val("");
                    $("textarea.content").val("");
                    const msg = publishMode[0].value==="publish" ? "Your article are published."
                        : "Your article will be visible to public on " + availableDate.toLocaleString();
                    alert("Congratulations " + msg);
                    console.log(resp);
                },
                error: (msg, status) => {
                    console.log("error!!");
                    console.log(status);
                    console.log(msg);
                },
                complete: () => {
                    console.log("loaded");
                }

            })
        });
    })
</script>