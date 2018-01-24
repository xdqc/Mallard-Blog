<article class="panel animated fadeInLeft">
    <img class="panel-img-top img-responsive" src="http://lorempixel.com/800/500"
         alt="random picture"/>
    <div class="panel-body">
        <h4 class="panel-title">${blog.getAuthor().getFName()} ${blog.getAuthor().getLName()}</h4>

        <h6 class="text-muted"> ${blog.getArticle().getLikeNum()} <span class="fa fa-thumbs-up"></span></h6>

        <p class="panel-text">${blog.getArticle().getContent()}</p>

        <a href="#" class="btn btn-primary">Read more</a>

        <a href="personal-blog" class="btn btn-info">Comments ${blog.getCommentTree()}</a>
        <c:set var="comments" value="${blog.getCommentTree()}"/>
        <%@include file="comments.jsp" %>
    </div>
</article>
<br>
