<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><span  class="glyphicon glyphicon-xbt" aria-hidden="true"></span>&nbsp;Welcome home!&nbsp;&nbsp;</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active">
                    <a class="navbar-brand" href="home-page"><span  class="glyphicon glyphicon-home" aria-hidden="true"></span>&nbsp;Home</a>
                </li>
                <li><a href="personal-blog?userId=${sessionScope.get("loggedInUser").getId()}">Personal Blog</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
            <c:if test="${not empty sessionScope.get('loggedInUser')}">
                <li><a href="#"><span  class="glyphicon glyphicon-user" aria-hidden="true"></span>${sessionScope.get('loggedInUser').getUserName()}</a></li>
                <li><a href="#"><span  class="glyphicon glyphicon-bell" aria-hidden="true"></span>Notification</a></li>
            </c:if>
                <li>
                    <div class="btn-group" role="group" aria-label="...">
                        <c:if test="${empty sessionScope.get('loggedInUser')}">
                        <a href="login" class="btn btn-default" role="button">Login</a>
                        </c:if>
                        <c:if test="${not empty sessionScope.get('loggedInUser')}">
                            <a href="login?logout=1" class="btn btn-default" role="button">Logout</a>
                        </c:if>
                    </div>
                </li>
                <li><a href="contact"><span  class="glyphicon glyphicon-envelope" aria-hidden="true"></span>Contact us</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span></button>
            </form>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
