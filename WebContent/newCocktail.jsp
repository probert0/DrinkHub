<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="logic.bean.CheckIngredientBean" %>
<%@page import="logic.bean.NewCocktailBean" %>
<%@page import="logic.controller.NewCocktailController" %>
<%@page import="logic.exception.StringIsEmptyException" %>
    
<%
	final NewCocktailBean newCBean;
	final NewCocktailController contr;

	if(request.getSession().getAttribute("newPostMessage") == null) {
		request.getSession().setAttribute("newPostMessage", "");
	}
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>New post page</title>
<style>
h3 {
	display: inline;
}
#formPost {
	width: 900px;
    margin: 0 auto;
}
</style>
</head>
<body>
<jsp:include page="/topBan.jsp"/>
<h4>->New cocktail</h4><hr>
<h4><font color="ff0000"><%=request.getSession().getAttribute("newPostMessage")%></font></h4>
<%request.getSession().setAttribute("newPostMessage", null);%>
	<div id="formPost">
		
		<form action="AddNewCocktailServlet" name="addIng" method="GET">
			<br><h3>Cocktail name: <input type="text" name="cocktailName" id="cocktailName"/></h3><br><br><br><br>
			<h3>Ingredients:</h3>
			<h4>Insert each ingredient separated by commas, for example: name,quantity,unity of measurement (ml or g or none)</h4>
			<h6>To insert more than eight ingredients, download desktop application!</h6>
			<h4>Ingredient 1: <input type="text" name="ingredient1" id="ingredient1"/>
				Ingredient 5: <input type="text" name="ingredient5" id="ingredient5"/><br>
				Ingredient 2: <input type="text" name="ingredient2" id="ingredient2"/>
				Ingredient 6: <input type="text" name="ingredient6" id="ingredient6"/><br>
				Ingredient 3: <input type="text" name="ingredient3" id="ingredient3"/>
				Ingredient 7: <input type="text" name="ingredient7" id="ingredient7"/><br>
				Ingredient 4: <input type="text" name="ingredient4" id="ingredient4"/>
				Ingredient 8: <input type="text" name="ingredient8" id="ingredient8"/></h4>
			<br><br><br><br>
			<h3>Procedure: <input type="text" name="procedure" id="procedure"/></h3><br><br><br><br>
			<h4>Insert tags separated by commas, for example: tag1,tag2,tag3</h4>
			<h6>Tags are not required</h6>
			<h3>Tags: <input type="text" name="tag" id="tag"/></h3><br><br><br><br>
			<input type="submit" value="Done"><br><br><br>
		</form>
		
	</div>
</body>
</html>