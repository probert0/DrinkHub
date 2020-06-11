package logic.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.bean.NewCocktailBean;
import logic.controller.NewCocktailController;

/**
 * Servlet implementation class AddNewCocktailServlet
 */
@WebServlet("/AddNewCocktailServlet")
public class AddNewCocktailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewCocktailBean newCBean = new NewCocktailBean();
	private NewCocktailController contr = new NewCocktailController();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewCocktailServlet() {
        super();
    }
    
    public void init() throws ServletException { 
    // empty method	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		String message = "newPostMessage";
		String ref = "Referer";
		
		Boolean ingrNotEmpty = ((request.getParameter("ingredient1") != "")|(request.getParameter("ingredient2") != "")|
				 		(request.getParameter("ingredient3") != "")|(request.getParameter("ingredient4") != "")|
				 		(request.getParameter("ingredient5") != "")|(request.getParameter("ingredient6") != "")|
				 		(request.getParameter("ingredient7") != "")|(request.getParameter("ingredient8") != ""));
		
		if(ingrNotEmpty && (request.getParameter("cocktailName") != "")&&(request.getParameter("procedure") != "")) {
        	
			String[] ingredients = {request.getParameter("ingredient1"),request.getParameter("ingredient2"),
									request.getParameter("ingredient3"),request.getParameter("ingredient4"),
									request.getParameter("ingredient5"),request.getParameter("ingredient6"),
									request.getParameter("ingredient7"),request.getParameter("ingredient8")};
        	String cTag = request.getParameter("tag");
        	String cName = request.getParameter("cocktailName");
        	String cProc = request.getParameter("procedure");
        	
       	
        	// add ingredients
    		for(int k = 0; k < ingredients.length; k++) {
    			if(ingredients[k] != "") {
    				String i = ingredients[k];
    				String[] currentIngr = addIngr(i);

    				if(currentIngr[0] != "0") {
    					String[] mess = {"ERROR: ingredients must be inserted like: name,quantity,unity of measurement.",
    									 "ERROR: Ingredient's quantity must be a number."};
    					Integer err = Integer.parseInt(currentIngr[0])-1;
                		request.getSession().setAttribute(message, mess[err]);
                		response.sendRedirect(request.getHeader(ref));
                		return;
                	}

    				if(currentIngr[3] == "3") {
                		request.getSession().setAttribute(message, "ERROR: Unity of measurement must be ml, g or none.");
                		response.sendRedirect(request.getHeader(ref));
                		return;
                    }
                    
                	this.newCBean.getRecipe().addIngredientBean(currentIngr[1], Float.parseFloat(currentIngr[2]), Integer.parseInt(currentIngr[3]));

    			}
    		}

        	
            // add tags
        	String[] cTagList = cTag.split(",");
        	addTags(cTagList);

        	
        	this.newCBean.setName(cName);
        	this.newCBean.getRecipe().setProcedure(cProc);
        	this.newCBean.setImage("logic/image/c1.png");
    		contr.newCocktailObject(newCBean);
    		
    		
    		request.getSession().setAttribute("bean",this.newCBean);
    		request.getSession().setAttribute("controller",contr);
    		request.getSession().setAttribute(message, " ");
    		response.sendRedirect(request.getHeader(ref));
    		return;
        	
        	
        }

		request.getSession().setAttribute(message, "ERROR: Input not valid.");
    	response.sendRedirect(request.getHeader(ref));
		
	}
	
	
	public String[] addIngr(String ingredient) throws ServletException {
		
		String[] ret = {"0","","",""}; // errorParseFloat, name, quantity, unityOfMeas
		
		String[] ingr = ingredient.split(",");
		if(ingr.length == 2 || ingr.length == 1){
			ret[0] = "1";
			return ret;
		}
		ret[1] = ingr[0];
		String q = ingr[1];
		try {
			Float.parseFloat(q);
    	} catch (NumberFormatException e) {
    		ret[0] = "2";
    		return ret;
    	}
		ret[2] = q;
    	String newt = "";
    	String type = ingr[2];
        if(type.equals("ml")) {
        	newt = "2"; //ml
        }
        else if(type.equals("g")) {
        	newt = "1"; //g
        }
        else if(type.equals("none")){
        	newt = "0"; //none (float)
        }
        else {
    		newt = "3";
        }
        ret[3] = newt;
		
		return ret;
		
		
	
	}
	
	
	public void addTags(String[] cTagList) throws ServletException, IOException {
    	
		if(cTagList[0] != "") {
    		for(int k = 0; k < cTagList.length; k++) {
    			this.newCBean.addTagBean(cTagList[k]);
    		}
        }
	
	}
	

}