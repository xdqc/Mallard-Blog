<%--
  Created by IntelliJ IDEA.
  User: qd16
  Date: 1/23/2018
  Time: 6:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
          integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"
            integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"
            integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ"
            crossorigin="anonymous"></script>

    <title>Personal Blog</title>
</head>
<body>

<div class="container">
    <div class="row">
        <%--personal information card--%>
        <div class="col-sm-12 col-md-3 col-lg-3">
            <div class="card">
                <img class="card-img-top img-responsive" src="http://i.pravatar.cc/300" alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title">${fname} ${lname}</h5>
                    <p class="card-text">${description}</p>
                </div>
                <dl class="list-group list-group-flush">
                    <div class="list-group-item">
                        <dt>Email:</dt>
                        <dd>${email}</dd>
                    </div>
                    <div class="list-group-item">
                        <dt>From:</dt>
                        <dd>${country}</dd>
                    </div>
                    <div class="list-group-item">
                        <dt>Age:</dt>
                        <dd>${age}</dd>
                    </div>
                </dl>
                <div class="card-body">
                    <a href="#" class="card-link">Follow me!</a>
                    <a href="#" class="card-link">Another link</a>
                </div>
            </div>
        </div>

        <%--list of blogs--%>
        <div class="col-sm-12 col-md-9 col-lg-9">
            <c:forEach var="blog" items="${articles}">
                <article class="card animated fadeInLeft">
                    <img class="card-img-top img-responsive" src="http://lorempixel.com/800/500" alt="random picture"/>
                    <div class="card-body">
                        <h4 class="card-title">${blog.getContent().substring(0,20)}</h4>

                        <h6 class="text-muted">Likes: ${blog.getLikeNum()}</h6>

                        <p class="card-text">${blog.getContent()}</p>

                        <a href="#" class="btn btn-primary">Read more</a>
                    </div>
                </article>
                <br>
            </c:forEach>
        </div>
    </div>

</div>
</body>
</html>
