<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search page</title>
<style>
h3 {
	display: inline;
}
#formSearch {
	width: 500px;
    margin: 0 auto;
}
</style>
</head>
<body>
<jsp:include page="/topBan.jsp"/>
<h4>->Search</h4><hr>
<br><br>
<div id="formSearch">
	<form action="SearchServlet" name="insertName" method="GET">
		<h3>Cocktail name: <input type="text" name="cocktailName" id="cocktailName"/></h3>
	<input type="submit" value="Search"></form>
</div>
<br>
<hr>

<%
	if((request.getSession().getAttribute("listing") == "Not found")||(request.getSession().getAttribute("listing") == "")) {
		%><h3><%=request.getSession().getAttribute("listing")%></h3><%
	}
	else if(request.getSession().getAttribute("listing") == null) {
		//do nothing
	}
	else {%>
		<h3><%=request.getSession().getAttribute("listing")%></h3><br><br>
		<%
		int l = (int)request.getSession().getAttribute("len");
		
		ArrayList<String> posts = (ArrayList<String>)request.getSession().getAttribute("results");
		for(int k=0; k<l; k++) {%>
			<h3><%=posts.get(k) + "=> See details"%></h3>
			<%String[] parts = posts.get(k).split(",");
			String id = parts[0];%>
			<form action="CocktailPostServlet" name="myform" method="GET"><input type="submit" name="id" value="<%=id%>"></form>
			<br>
		<%}
	}
	request.getSession().setAttribute("len", 0);
	request.getSession().setAttribute("listing","");
	//request.getSession().setAttribute("results", "");
%>


</body>
</html>