<%--getServletPath : <%= request.getServletPath() %><br>--%>
<%--getQueryString : <%= request.getQueryString() %><br>--%>
<%--getQueryString : <%= request.getSession().getAttributeNames() %><br>--%>

<c:forEach var="blog" items="${blogs}">
    <c:if test="${blog.getArticle().getShowHideStatus()>0 && blog.getAuthor().getIsvalid()>0}">
        <article class="panel panel-info">
            <div class="panel-heading">
                <h4 class="panel-title">${blog.getAuthor().getFName()} ${blog.getAuthor().getLName()}</h4>

            </div>
            <div class="panel-body">
                <div class="panel-text">
                                <span class="h5 text-muted"><span class="fa fa-clock-o"></span>
                                        ${blog.getArticle().getCreateTime().toLocalDateTime()}&nbsp;&nbsp;&nbsp;</span>
                    <span class="h5 text-muted"> ${blog.getArticle().getLikeNum()}&nbsp;<span class="fa fa-thumbs-up"></span></span>

                </div>

                <img class="panel-img-top img-responsive" src="https://picsum.photos/1000/400"
                     alt="random picture"/>

                    <%--display multimedia gallery here by ajax--%>
                <div id="multimedia-gallery-${blog.getArticle().getId()}"></div>

                <br>
                <div id="article-content-${blog.getArticle().getId()}" class="panel-text">
                        ${blog.getArticle().getContent().substring(0, Math.min(140, blog.getArticle().getContent().length()-1))}

                    <img id="load-article-content-img-${blog.getArticle().getId()}" src="pictures/loading.gif"
                         alt="loading..."
                         width="45" style="display: none;" aria-hidden="true">

                    <button id="read-more-${blog.getArticle().getId()}" class="btn btn-default btn-sm read-more-btn">
                        Read more
                    </button>
                </div>

                <div id="edit-article-area-${blog.getArticle().getId()}"></div>

                <br>
                <a href="/File-Upload?articleId=${blog.getArticle().getId()}" class="btn btn-primary">Upload
                    multimedia</a>
                <a href="/multimedia-gallery?articleId=${blog.getArticle().getId()}" class="btn btn-primary">Multimedia
                    Gallery</a>

                    <%--edit article button--%>
                <c:if test="${sessionScope.get('loggedInUser').equals(requestScope.get('browsingUser'))}">
                        <span class="edit-article-btn btn btn-default"
                              id="edit-article-btn-${blog.getArticle().getId()}"><span class="fa fa-pencil"></span> Edit</span>
                </c:if>

                <c:if test="${blog.getNumComments() > 0}">
                    <button type="button" id="showCommentBtn-${blog.getArticle().getId()}"
                            class="btn btn-info show-comment-btn">
                        <span class="badge">${blog.getNumComments()}</span>
                        Comments
                        <span id="comment-arrow-${blog.getArticle().getId()}" class="fa fa-chevron-down"></span>
                    </button>
                </c:if>
                <c:if test="${blog.getNumComments() == 0}">
                    <button type="button" id="show-comment-btn-${blog.getArticle().getId()}"
                            class="btn btn-info show-comment-btn" disabled="disabled"><span class="badge">0</span>
                        Comments
                    </button>
                </c:if>

                <img id="load-comment-img-${blog.getArticle().getId()}" src="pictures/loading.gif" alt="loading..."
                     width="60" style="display: none; padding:10px 0 0 20px" aria-hidden="true">
                <div id="comment-area-${blog.getArticle().getId()}" class="panel panel-default comment-area"
                     style="display: none">
                </div>
            </div>
        </article>
        <br>
    </c:if>
</c:forEach>



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
                publishBtn.append($("<span class='fa fa-paper-plane' aria-hidden='true'>").text(" Publish"));

            } else if (this.value === "draft") {
                datePicker.show();
                publishBtn.empty();
                publishBtn.append($("<span class='fa fa-floppy-o' aria-hidden='true'>").text(" Save"));
            }
        });

        let availableDate = new Date();
        let date = new Date();
        date = moment(date).format("YYYY-MM-DDTkk:mm");
        datePicker.val(date);

        publishBtn.on("click", function (e) {
            e.preventDefault();
            const title = $("input.title");
            const content = $("textarea.content");
            if (title.val()===""){
                swal({
                    title: "Need a title!",
                    text: "Write something interesting:",
                    type: "input",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    inputPlaceholder: "Write title"
                }, function (inputValue) {
                    if (inputValue === false) return false;
                    if (inputValue === "") {
                        swal.showInputError("You need to write a title!");
                        return false;
                    }
                    title.val(inputValue);
                });
                return;
            }

            if (content.val()===""){
                swal("Write something!", "You need to write content!", "warning");
                return;
            }

            if (publishMode[0].value==="draft"){
                if (datePicker.val()===""){
                    alert("Available time is required.");
                    return;
                }
                date = datePicker.val();
                date = moment(date).format("YYYY-MM-DDTkk:mm");
                availableDate = new Date(date);
            }

            const article = {};
            article["title"] = title.val();
            article["content"] = content.val();
            article["authorId"] = entityId($(this));
            article["createTime"] = new Date().getTime();
            article["validTime"] = availableDate.getTime();

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
                    swal("Congratulations ",msg,"success");
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