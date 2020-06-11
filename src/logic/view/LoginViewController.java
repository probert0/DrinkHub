package logic.view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import logic.controller.LoginController;
import logic.controller.SponsorController;
import logic.exception.UserNotExistException;

public class LoginViewController 
{
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private Button sublogin;
	
	@FXML
    private Button signButton;
	
	@FXML
    private Button guestButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;
    
    @FXML 
    private Label msg;
    
    @FXML
    public void signIn(ActionEvent event) throws IOException {
    	//go to registration
    	Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleSignUp.fxml"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new SignUpViewController());
		
		Switch.switchPage(event, p).show();
    	
    }
    
    @FXML
    public void enterAsGuest(ActionEvent event) throws IOException {
    	//go the search
    	Parent p2 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleHomepageGuest.fxml"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new SignUpViewController());
		
		Switch.switchPage(event, p2).show();
    }

    @FXML
    public void submit(ActionEvent event) throws IOException {
    	LoginController controller = LoginController.getInstance();
    	controller.getBean().setUsername(username.getText());
    	controller.getBean().setPassword(password.getText());
    	
    	try {
    		if(controller.findIdentity()) {
        		Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleHomepage.fxml"));
        		
        		FXMLLoader loader = new FXMLLoader();
        		loader.setController(new HomepageViewController());
        		
        		Switch.switchPage(event, p).show();
        		
        	}
        	else {
        		throw new UserNotExistException("This user doesn't exist.");
        	}
    	} catch (UserNotExistException e) {
    		this.msg.setText(e.getMessage());
    	}

    }
    
    @FXML
    public void initialize() {
    	SponsorController controller = SponsorController.getInstance();
    	controller.cleanSponsorDB();
    	signButton.setDisable(true);
    }
}