package logic.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import logic.bean.CocktailBean;
import logic.controller.FindCocktailController;

/**
 * FXML Controller class
 */
public class PostViewController implements Initializable {

	private FindCocktailController cocktailController = new FindCocktailController();
	
	
    @FXML
    Label label;
    
    @FXML
    Button button;
    
    @FXML
    private ImageView imagePost;

    @FXML
    private Label textLabel;
    
    @FXML
    private Text idCocktail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // nothing to do here
    }

    @FXML
    public void buttonClick(ActionEvent event) throws IOException {
    	CocktailBean cocktailBean = new CocktailBean();
    	cocktailBean.setId(getIdCocktail());
    	cocktailController.fillUpCocktailPage(cocktailBean);
    	Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleCocktailPage.fxml"));
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new CocktailPageViewController());
		Switch.switchPage(event, p).show();
    }

    public void setTitleText(String string) {
        label.setText(string);
    }
    
    public void setLabelTextRecip(String string) {
    	textLabel.setText(string);
    }
    
    public void setImageUrl(String string) throws FileNotFoundException {
    	imagePost.setImage(new Image(string));
    }

    public String getText() {
        return label.getText();
    }
    
    public void setIdCocktail(Integer integer) {
    	idCocktail.setText(integer.toString());
    }
    
    public Integer getIdCocktail() {
    	return Integer.parseInt(idCocktail.getText());
    }
    
    public void blockButton() {
    	button.setDisable(true);
    }

}