
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<%@include file="WEB-INF/_head.jsp"%>
    <title>Contact Us</title>
</head>
<body>
<%@include file="WEB-INF/_home_page_menu.jsp"%>
<div class="jumbotron jumbotron-sm">
    <div class="container">
        <div class="row">
            <div class="col-sm-12 col-lg-12">
                <h1 class="h1">Contact us <small>Feel free to contact us</small></h1>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <div class="row" style="margin-bottom: 300px">
        <div class="col-md-8">
            <div class="well well-sm">
                <form>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="name">
                                    Name</label>
                                <input type="text" class="form-control" id="name" placeholder="Enter name" required="required" />
                            </div>
                            <div class="form-group">
                                <label for="email">
                                    Email Address</label>
                                <div class="input-group">
                                <span class="input-group-addon"><span class="fa fa-envelope"></span>
                                </span>
                                    <input type="email" class="form-control" id="email" placeholder="Enter email" required="required" /></div>
                            </div>
                            <div class="form-group">
                                <label for="subject">
                                    Subject</label>
                                <select id="subject" name="subject" class="form-control" required="required">
                                    <option value="na" selected="">Choose One:</option>
                                    <option value="service">General Customer Service</option>
                                    <option value="suggestions">Suggestions</option>
                                    <option value="product">Product Support</option>
                                </select>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="name">
                                    Message</label>
                                <textarea style="overflow: auto; resize: none" name="message" id="message" class="form-control" rows="9" cols="25" required="required"
                                          placeholder="Message"></textarea>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <button type="submit" class="btn btn-primary pull-right" id="btnContactUs">
                                Send Message</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="col-md-4">
            <form>
                <legend><span class="fa fa-globe"></span> Our office</legend>
                <address>
                    <strong>University of Waikato</strong><br>
                    Gate 1 Knighton Road<br>
                    Private Bag 3105,<br>
                    Hamilton 3240, New Zealand<br>
                    <abbr title="Phone"><span class="fa fa-phone"></span></abbr>
                    12 345 6789
                </address>
                <address>
                    <strong>Web Master</strong><br>
                    <a href="mailto:#">mallard.blog@gmail.com</a>
                </address>
            </form>
        </div>
    </div>
</div>
<div class="col-sm-12">
    <%@ include file="WEB-INF/_foot.jsp" %>
</div>
</body>
</html>
