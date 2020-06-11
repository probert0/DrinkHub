package logic.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginView extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/logic/sample/SampleLogin.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		FXMLLoader loader = new FXMLLoader();
		loader.setController(new LoginViewController());
		stage.setResizable(false);
		
		stage.setTitle("DrinkHub");
	    stage.setScene(new Scene(root));
	    
	    stage.show();
	
	}

}
