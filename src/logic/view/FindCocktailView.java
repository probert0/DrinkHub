package logic.view;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.model.Ingredient;
import logic.model.Tag;

public class FindCocktailView {
	
	@FXML
	private ImageView cocktailImg;
	
	@FXML
	private javafx.scene.text.Text cocktailName;
	
	@FXML
	private javafx.scene.text.Text owner;
	
	@FXML
	private javafx.scene.control.TextArea procedure;
	
	@FXML
	private ListView<Tag> tagList;
	
	@FXML
	private ListView<Ingredient> ingredientsList;
	
	@FXML
	private Button searchCocktailBtn;
	
	
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/logic/sample/SampleFindCocktail.fxml"));

		FXMLLoader loader = new FXMLLoader();
		loader.setController(new FindCocktailViewController());
		
	    stage.setScene(new Scene(root));
	    stage.show();
	    
	}
	

}
