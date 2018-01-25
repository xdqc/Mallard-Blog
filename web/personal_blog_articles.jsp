<article class="panel animated fadeInLeft">
    <img class="panel-img-top img-responsive" src="http://lorempixel.com/800/500"
         alt="random picture"/>
    <div class="panel-body">
        <h4 class="panel-title">${blog.getAuthor().getFName()} ${blog.getAuthor().getLName()}</h4>

        <h6 class="text-muted"> ${blog.getArticle().getLikeNum()} <span class="fa fa-thumbs-up"></span></h6>

        <p class="panel-text">${blog.getArticle().getContent()}</p>

        <a href="#" class="btn btn-primary">Read more</a>

        <c:if test="${blog.getNumComments() > 0}">
            <button type="button" id="showCommentBtn-${blog.getArticle().getId()}" class="btn btn-info"> Show Comments (${blog.getNumComments()})</button>
        </c:if>
        <c:if test="${blog.getNumComments() == 0}">
            <button type="button" id="showCommentBtn-${blog.getArticle().getId()}" class="btn btn-info" disabled="disabled"> Show Comments (0)</button>
        </c:if>
        <div id="comment-area-${blog.getArticle().getId()}" class="">

        </div>

        <%--this is the normal approach to show data, we will replace it with AJAX--%>
        <%--<c:set var="comments" value="${blog.getCommentTree()}"/>--%>
        <%--<%@include file="comments.jsp" %>--%>

    </div>
</article>
<br>

<script>
    $("#showCommentBtn-${blog.getArticle().getId()}").on("click", function () {
        let commentArea = $("#comment-area-${blog.getArticle().getId()}");
        // Toggle comment-area display by click this button
        $(this).toggleClass('active');

        if ($(this).hasClass('active')){
            $.ajax({
                type: 'POST',
                url: 'personal-blog?=',
                data:  { blog: "${blog.getArticle().getId()}"},
                cache: false,
                beforeSend: function(){
                    commentArea.text('loading...')
                },
                success: function(resp, status){
                    // TODO make comments display nicely
                    commentArea.text(JSON.stringify(resp));
                },
                error: function (msg, status) {
                    commentArea.text(msg);
                },
                complete: function(){
                    commentArea.append(' loaded.')
                }
            });

            commentArea.show();
        } else {
            commentArea.hide();
        }

    })

</script>