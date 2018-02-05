<%--personal information panel--%>
<div class="panel user-panel" id="user-panel-${user.getId()}" data-spy="affix" data-offset-top="200">
    <!--img class="panel-img-top img-responsive" src="http://i.pravatar.cc/300" alt="panel image cap"-->
    <div id="showActivatedMultimedia-user-${blog.getArticle().getId()}" class="activated-multimedia"></div>
    <div class="panel-body" style="padding:10px;">
        <h5 class="panel-title">${fname} ${lname}
            <c:if test="${gender==0}">
                <span class="fa fa-venus" style="color: hotpink"></span>
            </c:if>
            <c:if test="${gender==1}">
                <span class="fa fa-mars" style="color: deepskyblue"></span>
            </c:if>
        </h5>
        <p class="panel-text">${description}</p>
    </div>
    <dl class="list-group dl-horizontal text-left">
        <div class="list-group-item">
            <dt><span class="fa fa-envelope"></span> Email</dt>
            <dd>${email}</dd>
        </div>
        <div class="list-group-item">
            <dt><span class="fa fa-map-marker"></span> From</dt>
            <dd>${country}</dd>
        </div>
        <div class="list-group-item">
            <dt><span class="fa fa-heart"></span> Age</dt>
            <dd>${age}</dd>
        </div>
    </dl>
    <div class="panel-body" style="padding: 10px;">
        <c:if test="${not empty sessionScope.get('loggedInUser')}">
            <c:if test="${!sessionScope.get('loggedInUser').equals(requestScope.get('browsingUser'))}">
                <a href="#" class="panel-link">Follow me!</a>
            </c:if>
            <c:if test="${sessionScope.get('loggedInUser').equals(requestScope.get('browsingUser'))}">
                <a href="edit_profile.jsp" class="panel-link">Edit my profile</a>
            </c:if>
        </c:if>
    </div>
</div>



