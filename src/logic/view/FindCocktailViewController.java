package logic.view;


import java.awt.Frame;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.bean.CocktailBean;
import logic.bean.IngredientBean;
import logic.bean.TagBean;
import logic.controller.FindCocktailController;
import logic.exception.StringIsEmptyException;
import logic.model.Cocktail;

public class FindCocktailViewController implements Initializable {
	
	
	private JTextField nameTagIngr = new JTextField();
	private JRadioButton nameBox = new JRadioButton("Name");
	private JRadioButton tagBox = new JRadioButton("Tag");
	private JRadioButton ingredientBox = new JRadioButton("Ingredient");
	private FindCocktailController cocktailController = new FindCocktailController();
	private List<Cocktail> cocktails = null;
	private ObservableList<String> cocktailsNameList = FXCollections.observableArrayList();
	private String currName = null;
	private String currOwner = null;
	private String selectedItem = null;
	private Integer currId = 0; 
	private String postedBy = "posted by: ";
	private ButtonGroup nti = new ButtonGroup();

	@FXML
	private ImageView cocktailImg;
	
	@FXML
	private javafx.scene.text.Text cocktailName;
	
	@FXML
	private javafx.scene.text.Text owner;
	
	@FXML
	private ListView<String> cocktailsList;

	@FXML
	private Button searchCocktailBtn;
	
	@FXML  
	private Button visitCocktailPageBtn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		visitCocktailPageBtn.setDisable(true);
		nti.add(nameBox);
    	nti.add(tagBox);
    	nti.add(ingredientBox);
		
	}
	
	@FXML
	private void search() throws StringIsEmptyException{
		this.cocktailsNameList = FXCollections.observableArrayList();
		nameTagIngr.setText("");
		cocktailsList.setItems(cocktailsNameList);
		nameBox.setSelected(true);
		
		JFrame f = new JFrame();
    	Object[] comp = new Object[] {"Search", nameTagIngr, "by:", nameBox, tagBox, ingredientBox};
    	
    	int optionSearch = JOptionPane.showConfirmDialog(f, comp, "Search cocktail",
    			JOptionPane.OK_CANCEL_OPTION);
    	
    	if (optionSearch == JOptionPane.OK_OPTION) {
    		
    		CocktailBean cName = new CocktailBean();
    		cName.setName(this.nameTagIngr.getText());
    		try {
				cocktailController.isInsertedCocktailNameValid(cName);	
			}catch (StringIsEmptyException e) {
				Frame j = new Frame();
				JOptionPane.showMessageDialog(j,"Write something", null , JOptionPane.OK_OPTION);
				return;
			}
    		if (nameBox.isSelected()) {
    			searchByName();
			} else if (tagBox.isSelected()) {
				searchByTag();
			} else if (ingredientBox.isSelected()) {
				searchByIngredient();
			} else {
				return;
			}
    		
    		
    		cocktailsList.setOnMouseClicked(new  EventHandler<MouseEvent>() {
    			@Override
    			public void handle(MouseEvent event) {
    				handleMouseEvent();
    			}
				
			});
    		
    				
		} else {
			f.dispose(); 
		}		
    	
	}
	
	private void handleMouseEvent() {
		visitCocktailPageBtn.setDisable(false);
		selectedItem = cocktailsList.getSelectionModel().getSelectedItem();
    	
		String[] m = selectedItem.split("\n");
		Integer g = Integer.parseInt(m[0]);
		for (Cocktail z : cocktails) {
			if (g == z.getId()) {
				cocktailName.setText("Cocktail name: " + z.getName());
				cocktailImg.setImage(new Image("logic/image/" + z.getImage()));
				owner.setText("Posted by: " + z.getCocktailUser()); 
			}
		}
		

	}

	private void searchByName() {
		CocktailBean cocktailNameBean = new CocktailBean();
		cocktailNameBean.setName(nameTagIngr.getText());
		this.cocktails = this.cocktailController.findCocktailWithName(cocktailNameBean);
		if (cocktails.isEmpty()) {
			Frame r = new Frame();
			JOptionPane.showMessageDialog(r,"No cocktail found with name " + nameTagIngr.getText(),
					null , JOptionPane.OK_OPTION);
			return;
		}
		for (Cocktail ctName : cocktails) {
			currName = ctName.getName();
			currOwner = ctName.getCocktailUser();
			currId = ctName.getId();
			cocktailsNameList.add(currId + "\n" + currName + "\n" + postedBy + currOwner);				
		} 
		cocktailsList.setItems(cocktailsNameList);
		

	}
	
	private void searchByTag() {
		TagBean cocktailTagBean = new TagBean(null);
		cocktailTagBean.setTagName(nameTagIngr.getText());
		this.cocktails = this.cocktailController.findCocktailWithTag(cocktailTagBean);
		if (cocktails.isEmpty()) {
			Frame k = new Frame();
			JOptionPane.showMessageDialog(k,"No cocktail found with tag " + nameTagIngr.getText(),
					null , JOptionPane.OK_OPTION);
			return;
		}
		for (Cocktail cocktailTag : cocktails) {
			currName = cocktailTag.getName();
			currOwner = cocktailTag.getCocktailUser();
			currId = cocktailTag.getId();
			cocktailsNameList.add(currId + "\n" + currName + "\n" + postedBy + currOwner);		
		}
		cocktailsList.setItems(cocktailsNameList);
		
		
	}
	
	private void searchByIngredient() {
		IngredientBean cocktailIngredientBean = new IngredientBean();
		cocktailIngredientBean.setName(nameTagIngr.getText());
		this.cocktails = this.cocktailController.findCocktailWithIngredient(cocktailIngredientBean);
		if (cocktails.isEmpty()) {
			Frame l = new Frame();
			JOptionPane.showMessageDialog(l,"No cocktail found with Ingredient " + nameTagIngr.getText(),
					null , JOptionPane.OK_OPTION);
			return;
		}
		for (Cocktail cocktailIngredient : cocktails) {
			currName = cocktailIngredient.getName();
			currOwner = cocktailIngredient.getCocktailUser();
			currId = cocktailIngredient.getId();
			cocktailsNameList.add(currId + "\n" + currName + "\n" + postedBy + currOwner);
		}
		cocktailsList.setItems(cocktailsNameList);
		
		
	}
	
	
	@FXML
	private void visitCocktailPage(javafx.event.ActionEvent event) throws IOException {
		String[] c = selectedItem.split("\n");
		CocktailBean cb = new CocktailBean();
		Integer a = Integer.parseInt(c[0]);
		cb.setId(a);
		cocktailController.fillUpCocktailPage(cb);
		/*cambiare il sample*/
		Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleCocktailPage.fxml"));
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new UserViewController());
		Switch.switchPage(event, p).show();
		
	}
	
	

	
	
}
