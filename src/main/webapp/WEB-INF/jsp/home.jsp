<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<link rel="icon" href="/static/favicon.ico">

<title>Paiso - Currency exchange</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
	integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
<link href="/static/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
<link href="/static/css/app.css" rel="stylesheet">
<link href="/static/css/bootstrap-datepicker3.standalone.min.css" rel="stylesheet">


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
				<a class="navbar-brand" href="#">Paiso</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse navbar-form navbar-right">
				<div class="user-profile-link">
					<i class="fa fa-user"></i>
					<span class='user-email'>
						<c:out value="${ user.user.email }" />
						<a href="/logout"><i class="fa fa-sign-out"></i></a>
					</span>
				</div>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</nav>

	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<div class="container">
			<h1>Paiso</h1>
			<p></p>
		</div>
	</div>

	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<div class="col-md-6">
				<h2>Exchange rate</h2>
				<div class="exchange-rate-section currency dropdown">
					<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
						Select a currency <span class="caret"></span>
					</button>
					<ul class="dropdown-menu currency-dropdown">
						<li><a href="#">AUD - Australian Dollar</a></li>
						<li><a href="#">CNY - Chinese Yuan</a></li>
						<li><a href="#">EUR - Euro</a></li>
						<li><a href="#">GBP - British Pound</a></li>
						<li><a href="#">JPY - Japanese Yen</a></li>
						<li><a href="#">USD - US Dollar</a></li>
					</ul>
				</div>
				<div class="exchange-rate-section input-group date" data-provide="datepicker">
					<input type="text" class="form-control datepicker" placeholder="Date">
					<div class="input-group-addon">
						<i class="glyphicon glyphicon-th"></i>
					</div>
				</div>

				<p class='exchange-rate-section exchange-rate-msg' style='display: none'>
					Exchange rate for 1 <span class='activeCurrency'></span>
				</p>
				<p class='exchange-rate'>
				<div class='table-responsive'>
					<table class='table table-striped'>
						<thead>
							<tr>
								<th>Currency</th>
								<th>Rate</th>
							</tr>
						</thead>
						<tbody class='exchange-rate-table-body'>
						</tbody>
					</table>
				</div>
				</p>
			</div>
			<div class="col-md-6">
				<h2>Recent Inquiries</h2>
				<div class='recent-inquiry-container table-responsive' style='display:none'>
					<table class='table table-striped'>
						<thead>
							<tr>
								<th>Currency</th>
								<th>Date</th>
							</tr>
						</thead>
						<tbody class='recent-inquiries-table-body'>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<hr>

		<footer>
			<p>
				Code licensed under the <a target="_blank" href="https://mit-license.org">MIT License <i class="fa fa-external-link"></i>
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
	<script src="/static/js/moment.js"></script>
	<script src="/static/js/app.js"></script>
	<script src="/static/js/bootstrap-datepicker.min.js"></script>
	<script src="/static/js/ie10-viewport-bug-workaround.js"></script>

</body>
</html>
