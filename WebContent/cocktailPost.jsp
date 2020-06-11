<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="logic.model.Cocktail" %>
    <%@page import="logic.controller.LoginController" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DrinkHub - Cocktail</title>
</head>
<body>
<jsp:include page="/topBan.jsp"/>
<h2><%=request.getSession().getAttribute("name")%></h2><br><br>
<h3>Ingredients:</h3>
<h4><%=request.getSession().getAttribute("ingr")%></h4><br><br>
<h4><%=request.getSession().getAttribute("proc")%></h4><br><br>
<%request.getSession().getAttribute("idDelete");%>
<%LoginController controller = LoginController.getInstance();
	if(controller.getBean().getTypeUser() == 2) {%>
	<form action="DeleteCocktailServlet" name="delete" method="GET"><input type="submit" name="del" value="DELETE"></form>	 
<%}%>
</body>
</html>