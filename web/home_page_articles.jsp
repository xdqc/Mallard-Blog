<div class="col-sm-12 col-md-9 col-lg-9">
    <c:forEach var="blog" items="${articles}">
        <c:if test="${blog.getShowHideStatus()>0}">
            <article class="card animated fadeInLeft">
                <img class="card-img-top img-responsive" src="http://lorempixel.com/800/500"
                     alt="random picture"/>
                <div class="card-body">
                    <h4 class="card-title">${blog.getContent().substring(0,20)}</h4>

                    <h6 class="text-muted"> ${blog.getLikeNum()} <span class="fa fa-thumbs-up"></span></h6>

                    <p class="card-text">${blog.getContent()}</p>

                    <a href="#" class="btn btn-primary">Read more</a>
                </div>
            </article>
            <br>
        </c:if>
    </c:forEach>
</div>