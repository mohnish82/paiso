<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<link rel="icon" href="/static/favicon.ico">

<title>Paiso - Signup</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

<link href="/static/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
<link href="/static/css/app.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">Paiso</a>
			</div>
		</div>
	</nav>

	<div>
		<c:if test="${ signUpSuccessful }">
			<div class='signUpSuccess'>
				Thank you for signing up! Please proceed to <a href="/">login</a>
			</div>
		</c:if>

		<c:if test="${not signUpSuccessful}">
			<sf:form class="form-signin" method="post" commandName="userAccountForm">

				<h2 class="form-signin-heading">Sign Up</h2>
				<sf:errors path="*" element="div" cssClass="errors" />

				<label for="inputEmail" class="sr-only">Email address</label>
				<sf:input path="email" type="email" id="inputEmail" cssClass="form-control" placeholder="Email address" required="true" autofocus="autofocus"/>

				<label for="inputPassword" class="sr-only">Password</label>
				<sf:password path="password" id="inputPassword" cssClass="form-control" placeholder="Password" required="true" />

				<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
			</sf:form>
		</c:if>

		<hr>

		<footer>
			<p>
				Code licensed under the <a target="_blank" href="https://mit-license.org">MIT License<i class="fa fa-external-link"></i>
				</a>.
			</p>
		</footer>
	</div>
	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>

	<script src="/static/js/ie10-viewport-bug-workaround.js"></script>

</body>
</html>
