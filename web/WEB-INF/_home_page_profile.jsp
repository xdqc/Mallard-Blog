<%--personal information card--%>
    <div class="card">
        <img class="card-img-top img-responsive" src="http://i.pravatar.cc/300" alt="Card image cap">
        <div class="card-body">
            <h5 class="card-title">${get} ${lname}
                <c:if test="${gender==0}">
                    <span class="fa fa-venus"></span>
                </c:if>
                <c:if test="${gender==1}">
                    <span class="fa fa-mars"></span>
                </c:if>
            </h5>
            <p class="card-text">${description}</p>
        </div>
        <dl class="list-group">
            <div class="list-group-item">
                <dt><span class="fa fa-envelope"></span> Followers:&nbsp;${follower_number}</dt>
            </div>
            <div class="list-group-item">
                <dt><span class="fa fa-map-marker"></span> Post:&nbsp;${post_number} </dt>
            </div>
            <div class="list-group-item">
                <dt><span class="fa fa-heart"></span> Notification:&nbsp; </dt>
            </div>
        </dl>
    </div>
