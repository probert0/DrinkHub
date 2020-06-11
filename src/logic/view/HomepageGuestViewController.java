package logic.view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import logic.controller.CocktailPostController;
import logic.exception.PostListIsNullException;

public class HomepageGuestViewController {
	
	@FXML
	private ListView<Pane> listView;
	
	
	 @FXML
	    public void initialize() throws IOException, PostListIsNullException {
	    	 	
	    	CocktailPostController con = CocktailPostController.getInstance();
	    	con.findSponsoredCocktail();
	    	String cocktailImage = ""; 
	    	
	    	ObservableList<Pane> listSponsor = FXCollections.observableArrayList();
	    	
	    	int lenPost;
	    	if(con.getBean().getPostList() == null) {
	    		lenPost = 0;
	    		FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/logic/sample/SamplePost.fxml"));
	            Pane listItem = fxmlLoad.load();
	            PostViewController control = fxmlLoad.getController();
	            control.setTitleText("NO POSTS");
	            control.blockButton();
	            listSponsor.add(listItem);
	    	}
	    	else {
	    		lenPost = con.getBean().getPostList().size();
	    	}
	    	
	    	for (int j = 0; j < lenPost; j++) {
	    		
	            FXMLLoader fxmlL = new FXMLLoader(getClass().getResource("/logic/sample/SamplePost.fxml"));
	            Pane listItems = fxmlL.load();
	            PostViewController control = fxmlL.getController();
	            control.setTitleText(con.getBean().getPostList().get(j).getName());
	            control.setLabelTextRecip(con.getBean().getPostList().get(j).getDate().toString());
	            control.setIdCocktail(con.getBean().getPostList().get(j).getId());
	            control.blockButton();
	            //prendere stringa dal db che ha url dell'immagine
	            cocktailImage = con.getBean().getPostList().get(j).getImage();
	            control.setImageUrl("logic/"+cocktailImage);
	            
	            listSponsor.add(listItems);
	        }
	    	listView.setFocusTraversable( false );
	        listView.getItems().addAll(listSponsor);
	    }

}
