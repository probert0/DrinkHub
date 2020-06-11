package logic.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrePayView extends Application 
{

	@Override
	public void start(Stage stage) throws Exception 
	{
		Parent root = FXMLLoader.load(getClass().getResource("/logic/sample/SamplePrePay.fxml"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new PrePayViewController());
		
		
		stage.setScene(new Scene(root));
		stage.setTitle("DrinkHub - Payment page");
	    stage.show();
		
	}

}
