package logic.view;

import java.awt.Button;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomepageView extends Application{
	
	@FXML
	private AnchorPane anchorHomepage;
	
	@FXML
	private ButtonBar btnBar;
	
	@FXML
	private Button btnSearch;
	
	@FXML
	private Button btnSearchUsr;
	
	@FXML
	private Button btnNewPost;
	
	@FXML
	private Button btnUserPage;
	
	@FXML
	private ImageView banImage;
	
	@FXML
	private Label welcome;

	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/logic/sample/SampleHomepage.fxml"));

		FXMLLoader loader = new FXMLLoader();
		loader.setController(new HomepageViewController());
		
		
	    stage.setScene(new Scene(root));
	    stage.setTitle("DrinkHub - Homepage");
	    stage.show();
	    
	    
	    
	}

}
