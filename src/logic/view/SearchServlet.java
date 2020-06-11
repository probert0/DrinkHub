package logic.view;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.bean.CocktailBean;
import logic.controller.FindCocktailController;
import logic.model.Cocktail;

/**
 * Servlet implementation class Search
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FindCocktailController cocktailController = new FindCocktailController();
	private String search = "/search.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cocktailSearch = new String(request.getParameter("cocktailName").getBytes("iso-8859-1"), StandardCharsets.UTF_8);
		String cocktailN;

		List<Cocktail> cocktails = null;
		if (cocktailSearch.equals("")) {
			request.getSession().setAttribute("searchMessage","Retry. Field name is not correct.");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(search);
			dispatcher.forward(request,response);
		}
		else {
			request.getSession().setAttribute("searchMessage","Results for '"+ cocktailSearch +"':");			

			CocktailBean cocktailNameBean = new CocktailBean();
			cocktailNameBean.setName(cocktailSearch);
		
			cocktails = this.cocktailController.findCocktailWithName(cocktailNameBean);
			if (cocktails.isEmpty()) {
				//("No cocktail found.")
				request.getSession().setAttribute("listing", "Not found");
    			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(search);
    			dispatcher.forward(request,response);

			} 
			else {
				request.getSession().setAttribute("listing", "Results for '"+ cocktailSearch + "':");
				List<String> cocktailsNameList = new ArrayList<>();
				for (Cocktail ctName : cocktails) {
					cocktailN = ctName.getId() + ", "+ ctName.getName() + ", posted by " + ctName.getCocktailUser();
					cocktailsNameList.add(cocktailN);	
				}
				request.getSession().setAttribute("len", cocktailsNameList.size());
				request.getSession().setAttribute("results", cocktailsNameList);
    			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(search);
    			dispatcher.forward(request,response);
			} 
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
