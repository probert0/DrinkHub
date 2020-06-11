package logic.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import logic.bean.ProfileBean;
import logic.controller.LoginController;

public class TopBanViewController {
	
	@FXML
    private ImageView banImageId;

	@FXML
	private Button btnHome;
	 
    @FXML
    private Button btnSearch;

    @FXML
    private Button btnSearchUser;

    @FXML
    private Button btnNewPost;

    @FXML
	private Button btnUserPage;
    
    @FXML
    private Button exit;
    
    private static TopBanViewController inst;
    
    public static TopBanViewController getInstance() {
        if (inst == null)
            inst = new TopBanViewController();
        return inst;
    }


    @FXML
    void exitPage(ActionEvent event) throws IOException {
    	//exit from app to main page
    	Parent p2 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleLogin.fxml"));
	    FXMLLoader loader = new FXMLLoader();
	    loader.setController(new LoginViewController());
	    Switch.switchPage(event, p2).show();
    }

    @FXML
    void newPost(ActionEvent event) throws IOException {
    	Parent p2 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleNewCocktail.fxml"));
	    FXMLLoader loader = new FXMLLoader();
	    loader.setController(new NewCocktailViewController());
	    Switch.switchPage(event, p2).show();
    }

    @FXML
    void searchPost(ActionEvent event) throws IOException {
    	
    	Parent p4 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleFindCocktail.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new FindCocktailViewController());
        Switch.switchPage(event, p4).show();
    }

    @FXML
    void searchUser(ActionEvent event) throws IOException {
    	//search user
    	
    	
    	Parent p3 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleFindBarUser.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new FindBarUserViewController());
        Switch.switchPage(event, p3).show();
    }

    @FXML
    void showUser(ActionEvent event) throws IOException {
    	//show user page
    	LoginController controller = LoginController.getInstance();
    	
    	ProfileBean.setUsername(controller.getBean().getUsername());
    	ProfileBean.setUName(controller.getBean().getName());
    	ProfileBean.setSurname(controller.getBean().getSurname());
    	ProfileBean.setImage(controller.getBean().getImage());
    	Parent p3 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleUserPage.fxml"));

        FXMLLoader loader = new FXMLLoader();
        loader.setController(new UserPageViewController());
        
        Switch.switchPage(event, p3).show();
    }
    
    public void goHome(ActionEvent event) throws IOException {
    	Parent p3 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleHomepage.fxml"));
        
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new HomepageViewController());

        Switch.switchPage(event, p3).show();
    }

}
