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
<%@include file="head.jsp"%>
    <title>Personal Blog</title>
</head>
<body>
<%@include file="home_page_logo.jsp"%>
<%@include file="home_page_menu.jsp"%>

<div class="container">
    <div class="row">
        <%--personal information panel--%>
        <div class="col-sm-12 col-md-3 col-lg-3">
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
                <dl class="list-group">
                    <div class="list-group-item">
                        <dt><span class="fa fa-envelope"></span> Email: </dt>
                        <dd>${email}</dd>
                    </div>
                    <div class="list-group-item">
                        <dt><span class="fa fa-map-marker"></span> From: </dt>
                        <dd>${country}</dd>
                    </div>
                    <div class="list-group-item">
                        <dt><span class="fa fa-heart"></span> Age: </dt>
                        <dd>${age}</dd>
                    </div>
                </dl>
                <div class="panel-body">
                    <a href="#" class="panel-link">Follow me!</a>
                    <a href="#" class="panel-link">Another link</a>
                </div>
            </div>
        </div>

        <%--list of blogs--%>
        <div class="col-sm-12 col-md-9 col-lg-9">
            <c:forEach var="blog" items="${articles}">
                <c:if test="${blog.getShowHideStatus()>0}">
                    <article class="panel animated fadeInLeft">
                        <img class="panel-img-top img-responsive" src="http://lorempixel.com/800/500"
                             alt="random picture"/>
                        <div class="panel-body">
                            <h4 class="panel-title">${blog.getContent().substring(0,20)}</h4>

                            <h6 class="text-muted"> ${blog.getLikeNum()} <span class="fa fa-thumbs-up"></span></h6>

                            <p class="panel-text">${blog.getContent()}</p>

                            <a href="#" class="btn btn-primary">Read more</a>
                        </div>
                    </article>
                    <br>
                </c:if>
            </c:forEach>
        </div>
    </div>

</div>
</body>
</html>
