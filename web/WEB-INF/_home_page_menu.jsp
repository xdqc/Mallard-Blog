 <!-- Collect the nav links, forms, and other content for toggling -->
        <nav class="navbar navbar-inverse">
            <nav class="navbar navbar-inverse" style="margin-left: -1px;">
                <div class="container-fluid">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="navbar-header">
                        <li><img src="/pictures/Logo.png" alt="Logo" width="80"  height="50" style="float: left; margin-top: 11px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#bs-example-navbar-collapse-1" aria-expanded="false" style="float: right">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>
          <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav" id="set">
                <li>
                    <a class="navbar-brand nav-button active" href="home-page">
                        <span class="glyphicon glyphicon-home"
                              aria-hidden="true"></span>&nbsp;Home</a>&nbsp;&nbsp;&nbsp;
                </li>
            </ul>

              <ul class="nav navbar-nav" id="set1">
                  <li><a class="navbar-brand nav-button active" href="personal-blog?userId=${sessionScope.get("loggedInUser").getId()}">
                      <span class="fa fa-user-o" aria-hidden="true"></span>&nbsp;Personal Blog</a></li>
              </ul>

            <ul class="nav navbar-nav navbar-right">
                <c:if test="${not empty sessionScope.get('loggedInUser')}">

                    <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;${sessionScope.get('loggedInUser').getUserName()}</a>
                    <ul class="dropdown-menu">
                        <li><a href="edit_profile.jsp">Edit Profile</a></li>
                        <li><a href="#">Delete account</a></li>
                        <li><a href="#">Re-set Password</a></li>
                    </ul>
                    </li>
                    <li><a href="#"><span class="glyphicon glyphicon-bell" aria-hidden="true"></span>&nbsp;Notification</a>
                    </li>
                </c:if>
                <li>
                    <div class="btn-group" role="group" aria-label="...">
                        <c:if test="${empty sessionScope.get('loggedInUser')}">
                            <form action="login?login=0" method="post" style="margin-bottom: 0px; margin-top: 8px;">
                                <button type="submit" class="btn btn-default">Login/SignUp</button>&nbsp;&nbsp;
                            </form>
                        </c:if>
                        <c:if test="${not empty sessionScope.get('loggedInUser')}">
                            <form action="login?logout=1" method="post" style="margin-bottom: 0px; margin-top: 8px;">
                                <button type="submit" class="btn btn-default" role="button">Logout</button>&nbsp;&nbsp;
                            </form>
                        </c:if>
                    </div>
                </li>
                <li><a class="navbar-brand" href="contact"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>&nbsp;Contact
                    Us</a></li>
            </ul>
            <form class="navbar-form navbar-right" action="search" method="get">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search" name="search">
                </div>
                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-zoom-in"
                                                                    aria-hidden="true"></span></button>
            </form>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
 </nav>
</nav>
