<%--personal information panel--%>
<div class="panel">
    <img class="panel-img-top img-responsive" src="http://i.pravatar.cc/300" alt="panel image cap">
    <div class="panel-body">
        <h5 class="panel-title">${fname} ${lname}
            <c:if test="${gender==0}">
                <span class="fa fa-venus"></span>
            </c:if>
            <c:if test="${gender==1}">
                <span class="fa fa-mars"></span>
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
    <div class="panel-body">
        <c:if test="${not empty sessionScope.get('loggedInUser')}">
            <c:if test="${!sessionScope.get('loggedInUser').equals(sessionScope.get('browsingUser'))}">
                <a href="#" class="panel-link">Follow me!</a>
            </c:if>
            <c:if test="${sessionScope.get('loggedInUser').equals(sessionScope.get('browsingUser'))}">
                <a href="edit_profile.jsp" class="panel-link">Edit my profile</a>
            </c:if>
        </c:if>
    </div>
</div>

<style type="text/css">
    .list-group-item dt {
        width:auto;
        margin-left:auto;
    }
    .list-group-item dd {
        width:auto;
        margin-left:80px;
    }
</style>

