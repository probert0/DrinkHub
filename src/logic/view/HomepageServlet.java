package logic.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.controller.CocktailPostController;
import logic.exception.PostListIsNullException;

/**
 * Servlet implementation class HomepageServlet
 */
@WebServlet("/HomepageServlet")
public class HomepageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomepageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CocktailPostController con = CocktailPostController.getInstance();
    	con.findSponsoredCocktail();
    	try {
			if(con.getBean().getPostList() == null) {
				//qui mette 'no posts'
				request.getSession().setAttribute("postMessage","NO SPONSORED POSTS");
				String nextPage = "/Homepage.jsp";
				RequestDispatcher dis= getServletContext().getRequestDispatcher(nextPage);
				dis.forward(request,response);
			    return;
			}
			else {
				request.getSession().setAttribute("len",con.getBean().getPostList().size());
				request.getSession().setAttribute("listSponsor",con.getBean().getPostList());
			}
		} catch (PostListIsNullException | ServletException | IOException e) {
			e.printStackTrace();
		} 
    	
		String nextPage = "/Homepage.jsp";
		RequestDispatcher dispa= getServletContext().getRequestDispatcher(nextPage);
		dispa.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
