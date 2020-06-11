package logic.view;

import java.io.IOException;

import com.lynden.gmapsfx.GoogleMapView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class FindBarUserView {
	
	@FXML
	private Button btnBack;
	
	@FXML
	private Button btnSearch;
	
	@FXML
	private Button btnVisit;
	
	@FXML
	private ListView<String> userListByName;
	
	@FXML
	private GoogleMapView mapView;
	
	
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/logic/sample/SampleFindBarUser.fxml"));

		FXMLLoader loader = new FXMLLoader();
		loader.setController(new FindBarUserViewController());
		
	    stage.setScene(new Scene(root));
	    stage.show();
	    
	}

}
