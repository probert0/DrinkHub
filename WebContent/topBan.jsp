<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="LoginBean" scope="request" class="logic.view.LoginServlet"/>
<jsp:setProperty name="LoginBean" property="*"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style>
form { 
	display: inline;
}
p {
	display: inline;
}
#container {
	width: 680px;
    margin: 0 auto;
}
</style>
</head>
<body>
<div id="container">
	<p>DrinkHub</p>
	<form action="HomepageServlet" name="myform" method="GET">
		<input type="submit" value="Homepage">
	</form>
	
	<form action="ProfileServlet" name="myform" method="GET">
		<input type="submit" value="My profile">
	</form>
	
	<form action="NewPostServlet" name="myform" method="GET">
		<input type="submit" value="New cocktail">
	</form>
	
	<form action="search.jsp" name="myform" method="GET">
		<input type="submit" value="Search cocktail">
	</form>
	
	<form action="FindBar.jsp" name="myform" method="GET">
		<input type="submit" value="Search bar">
	</form>
	
	<form action="SponsorServlet" name="myform" method="GET">
		<input type="submit" value="Sponsor">
	</form>
	
	<form action="ExitServlet" name="myform" method="GET">
		<input type="submit" value="Exit">
	</form>
	<hr>
</div>
</body>
</html>