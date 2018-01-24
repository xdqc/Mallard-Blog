<c:if test="${articles.size()==0}">
    <div class="jumbotron">
        <h3>This user has no articles</h3>
        <p><a class="btn btn-primary btn-lg" href="home-page" role="button">Back to Home</a></p>
    </div>
</c:if>

<c:if test="${articles.size()!=0}">
    <c:forEach var="article" items="${articles}">
        <c:if test="${article.getShowHideStatus()>0}">
            <article class="panel animated fadeInLeft">
                <img class="panel-img-top img-responsive" src="http://lorempixel.com/800/500"
                     alt="random picture"/>
                <div class="panel-body">
                    <h4 class="panel-title">${article.getContent().substring(0,20)}</h4>

                    <h6 class="text-muted"> ${article.getLikeNum()} <span class="fa fa-thumbs-up"></span></h6>

                    <p class="panel-text">${article.getContent()}</p>

                    <a href="#" class="btn btn-primary">Read more</a>

                    <a href="personal-blog" class="btn btn-info">Comments ${blogs.get(article)}</a>
                    <c:set var="comments" value="${blogs.get(article)}"/>
                    <%@include file="comments.jsp" %>
                </div>
            </article>
            <br>
        </c:if>
    </c:forEach>
</c:if>