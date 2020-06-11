package logic.view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.bean.LoginBean;
import logic.controller.CocktailPostController;
import logic.controller.LoginController;
import logic.exception.PostListIsNullException;

public class HomepageViewController {
	
	@FXML
    private Label welcome;

    @FXML
    private ImageView banImage;

    @FXML
    private Button sponsor;
    
    @FXML
	private Button btnSearch;
    
    @FXML
   	private Button btnSearchUsr;

    
    @FXML
   	private Button btnUserPage;
    
    @FXML
    private Label sponsorMessage;
    
    
    @FXML
	private Button btnNewPost;
    
    @FXML
	private ListView<Pane> listView;
    
    @FXML
    void promote(ActionEvent event) throws IOException {
    	Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleSponsor.fxml"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new HomepageViewController());

		Switch.switchPage(event, p).show();
    }
    
        
    @FXML
    void searchUsr(ActionEvent event) throws IOException {
   		Parent p3 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleFindBarUser.fxml"));
   		FXMLLoader loader = new FXMLLoader();
        loader.setController(new HomepageViewController());
        Switch.switchPage(event, p3).show();
    	
    }
    
    @FXML
    void post(ActionEvent event) throws IOException {
      Parent p2 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleNewCocktail.fxml"));
    
      FXMLLoader loader = new FXMLLoader();
      loader.setController(new NewCocktailViewController());

      Switch.switchPage(event, p2).show();
    }

    
    @FXML
    void page(ActionEvent event) throws IOException {
    	Parent p3 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleUserPage.fxml"));
        
    	FXMLLoader loader = new FXMLLoader();
    	loader.setController(new UserViewController());

    	Switch.switchPage(event, p3).show();
    	
    }
    
    
    @FXML
    public void initialize() throws IOException, PostListIsNullException {
    	
    	LoginController k = LoginController.getInstance();
    	LoginBean bean = k.getBean();
    	
    	if(bean.getTypeUser() == 1) {
    		sponsor.setVisible(true);
    		sponsorMessage.setVisible(true);
    	}
    	else {
    		sponsor.setVisible(false);
    		sponsorMessage.setVisible(false);
    	}
    	
    	
    	CocktailPostController con = CocktailPostController.getInstance();
    	con.findSponsoredCocktail();
    	String cocktailImage = ""; 
    	
    	ObservableList<Pane> list = FXCollections.observableArrayList();
    	
    	int lengPost;
    	if(con.getBean().getPostList() == null) {
    		lengPost = 0;
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/logic/sample/SamplePost.fxml"));
            Pane listItem = fxmlLoader.load();
            PostViewController controller = fxmlLoader.getController();
            controller.setTitleText("NO POSTS");
            controller.blockButton();
            list.add(listItem);
    	}
    	else {
    		lengPost = con.getBean().getPostList().size();
    	}
    	
    	for (int j = 0; j < lengPost; j++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/logic/sample/SamplePost.fxml"));
            Pane listItem = fxmlLoader.load();
            PostViewController controller = fxmlLoader.getController();
            controller.setTitleText(con.getBean().getPostList().get(j).getName());
            controller.setLabelTextRecip(con.getBean().getPostList().get(j).getDate().toString());
            controller.setIdCocktail(con.getBean().getPostList().get(j).getId());
            //prendere stringa dal db che ha url dell'immagine
            cocktailImage = con.getBean().getPostList().get(j).getImage();
            controller.setImageUrl("logic/"+cocktailImage);
            
            list.add(listItem);
        }
    	listView.setFocusTraversable( false );
        listView.getItems().addAll(list);
    }
    
   

}

