package logic.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.controller.LoginController;
import logic.controller.SponsorController;

/**
 * Servlet implementation class SponsorServlet
 */
@WebServlet("/SponsorServlet")
public class SponsorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SponsorServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("name") == null) {
			//not logged
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
			dispatcher.forward(request,response);
		}
		else {
			//logged
			SponsorController controller = SponsorController.getInstance();
			
			String usernameSponsor = LoginController.getInstance().getBean().getUsername();
			
			controller.getBean().setUserSponsor(usernameSponsor);
			controller.searchExistingSponsor();
			
			if(LoginController.getInstance().getBean().getTypeUser() != 1) {
				String nextJSPNoBar = "/NoBarSponsorPage.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSPNoBar);
				dispatcher.forward(request,response);
			}
			
			else if ( controller.getBean().getTimeSponsor() != null) {
				request.getSession().setAttribute("timeline",controller.getBean().getTimeSponsor());
				String nextJSP = "/noSponsorPage.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
				dispatcher.forward(request,response);
			}
			
			
			else {
				String nextJSP = "/sponsorPage.jsp";
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
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
