package logic.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class TopBanGuestViewController {
	
	@FXML
    private Button btnSearch;
	
	@FXML
    private ImageView banImageId;
    
    @FXML
    private Button exit;

    @FXML
    private Button btnNewPost;

    @FXML
	private Button btnUserPage;
    
    @FXML
    private Button btnSignUp;
    
    @FXML
    private Button btnSearchUser;
    
    @FXML
	private Button btnHome;
    
    public void initialize() {
    	this.btnNewPost.setDisable(true);
    	this.btnSearch.setDisable(true);
    	this.btnSearchUser.setDisable(true);
    	
    }
    
    @FXML
    public void goToSignUp(ActionEvent event) throws IOException {
    	Parent p2 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleSignUp.fxml"));
	    FXMLLoader loader = new FXMLLoader();
	    loader.setController(new NewCocktailViewController());
	    Switch.switchPage(event, p2).show();
    }
    
    @FXML
    void exitPage(ActionEvent event) throws IOException {
    	//exit from app to main page
    	Parent p2 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleLogin.fxml"));
	    FXMLLoader loader = new FXMLLoader();
	    loader.setController(new LoginViewController());
	    Switch.switchPage(event, p2).show();
    }

}
