package logic.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.bean.IDBean;
import logic.controller.CocktailPageController;

/**
 * Servlet implementation class AddNewCocktailServlet
 */
@WebServlet("/DeleteCocktailServlet")

public class DeleteCocktailServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
		private IDBean idBean = new IDBean();
		private CocktailPageController contr = new CocktailPageController();
	       
	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	    public DeleteCocktailServlet() {
	        super();
	    }
	    
	    public void init() throws ServletException { 
	    // empty method	
	    }

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
			Integer id = (Integer) request.getSession().getAttribute("idDelete");
    		idBean.setId(id);
    		contr.removeCocktailC(idBean);
			
    		String backHomepage = "/Homepage.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(backHomepage);
            dispatcher.forward(request,response);
			
		}

}
