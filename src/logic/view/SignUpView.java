package logic.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignUpView extends Application {
	@Override
	public void start(Stage stage) throws Exception 
	{
		Parent root = FXMLLoader.load(getClass().getResource("/logic/sample/SampleSignIn.fxml"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new PrePayViewController());
		
		
		stage.setScene(new Scene(root));
		stage.setTitle("DrinkHub - Sign In");
	    stage.show();
		
	}
}
