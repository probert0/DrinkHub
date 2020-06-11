package logic.view;

import java.awt.Component;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.bean.CocktailPageBean;
import logic.bean.IDBean;
import logic.controller.CocktailPageController;
import logic.controller.LoginController;
import logic.model.Ingredient;
import logic.model.Tag;


public class CocktailPageViewController {
	
	
	private IDBean idBean;
	
	private CocktailPageController contr;
	
	
	 @FXML
	 private Button deleteButton;
	 
	 @FXML
	 private Label procedure;
	 
	 @FXML
	 private Label ingredients;
	 
	 @FXML
	 private Label tags;
	 
	 @FXML
	 private Label cocktailName;
	 
	 @FXML
	 private Label cocktailUsername;
	 
	 @FXML
	 private Label cocktailDate;
	 
	 @FXML
	 private ImageView cocktailImage;
	 
	
	 public void initialize() {
		 this.idBean = new IDBean();
		 this.contr = new CocktailPageController();
		 LoginController controller = LoginController.getInstance();
		 
		 if(controller.getBean().getTypeUser() == 2) {
			 this.deleteButton.setDisable(false);
			 this.deleteButton.setVisible(true);
		 }
		 
		 String cName = CocktailPageBean.getName();
		 String cUser = CocktailPageBean.getUser();
		 String cProcedure = CocktailPageBean.getRecipe().getProcedure();
		 Date cDate = CocktailPageBean.getDate();
		 String cImage = CocktailPageBean.getImage();
		 int cID = CocktailPageBean.getId();
		 List<Tag> cTagList = CocktailPageBean.getTags();
		 List<Ingredient> cIngrList = CocktailPageBean.getRecipe().getIngredients();
		 
		 idBean.setId(cID);
		 
		 this.cocktailName.setText(cName);
		 this.cocktailUsername.setText(cUser);
		 this.procedure.setText(cProcedure);
		 this.cocktailDate.setText(cDate.toString());
		 
		 String newLine = "\n";
		 
		 String cTags = "";
		 int lenTags = cTagList.size();
		 for(int i = 0; i < lenTags; i++) {
			 String t = cTagList.get(i).getTag();
			 cTags = cTags.concat(t);
			 cTags = cTags.concat(newLine);
		 }
		 this.tags.setText(cTags);
		 
		 String cIngr = "";
		 int lenIngr = cIngrList.size();
		 for(int i = 0; i < lenIngr; i++) {
			 String ingrName = cIngrList.get(i).getName();
			 String ingrQuant = cIngrList.get(i).getQuantity().toString();
			 int t = cIngrList.get(i).getType();
			 String ingr = "";
			 if(t == 1) {
				 ingr = ingrName + " (" + ingrQuant + " g)\n";
			 }
			 else if(t == 2) {
				 ingr = ingrName + " (" + ingrQuant + " ml)\n";
			 }
			 else {
				 ingr = ingrName + " (" + ingrQuant + ")\n";
			 }
			 cIngr = cIngr.concat(ingr);
		 }
		 this.ingredients.setText(cIngr);
		 
		 this.cocktailImage.setImage(new Image("logic/"+cImage));	 

	 }
	 
	 
	 @FXML
	 void deleteCocktail(ActionEvent event) throws IOException {
		 
		 Component g = null;
		 int option = JOptionPane.showConfirmDialog(g, "Do you want to delete this cocktail?");
		 if(option == JOptionPane.OK_OPTION) {
			 contr.removeCocktailC(idBean);
		 }
		 Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleHomepage.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setController(new HomepageViewController());
			Switch.switchPage(event, p).show();
		 
	 }

}
