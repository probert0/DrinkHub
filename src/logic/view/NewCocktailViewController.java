package logic.view;

import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.bean.CheckIngredientBean;
import logic.bean.CheckTagBean;
import logic.bean.IngredientBean;
import logic.bean.NewCocktailBean;
import logic.controller.NewCocktailController;
import logic.exception.IngredientListIsEmptyException;
import logic.exception.StringIsEmptyException;


public class NewCocktailViewController {
	
	 private NewCocktailBean newCBean;
	 
	 private NewCocktailController contr;
	 
	 private List<String> imagesList;
	 
	 private int count;
	
	 @FXML
	 private Button doneButton;
	
	 @FXML
	 private Button addIngr;
	
	 @FXML
	 private Button addTags;
	
	 @FXML
	 private Button removeIngr;
	
	 @FXML
	 private Button removeTags;
	
	 @FXML
	 private ImageView banImage;

	 @FXML
	 private Button sponsor;
	    
	 @FXML
	 private Button btnSearch;
	    
	 @FXML
	 private Button btnSearchUsr;
	 
	 @FXML
	 private Button btnNewPost;
	 
	 @FXML
	 private TextArea addProcedure;
	 
	 @FXML
	 private ListView<String> addIngredients;
	 
	 @FXML
	 private ListView<String> addTagsL;

	 @FXML
	 private TextArea addDrinkName;
	 
	 @FXML
	 private Button backNC;
	 
	 @FXML 
	 private Label errMsg;
	 
	 @FXML
	 private ImageView image;
	 
	 @FXML
	 private Button next;
	 
	 @FXML
	 private Button prev;
	 

	 public void initialize() {
		 
		 this.newCBean = new NewCocktailBean();
		 this.contr = new NewCocktailController();
		 this.removeTags.setDisable(true);
		 this.removeIngr.setDisable(true);
		 this.count = 0;
		 this.imagesList = new ArrayList<>();
		 for(int i = 1; i < 41; i++) {
			 String newImage = "logic/image/c"+i+".png";
			 this.addToImagesList(newImage);
		 }
		 
	     image.setImage(new Image(imagesList.get(0)));
	 }

		 
	@FXML
	void backNewCocktail(ActionEvent event) throws IOException {
		
		Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleHomepage.fxml"));
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new HomepageViewController());
		Switch.switchPage(event, p).show();
	
	}
	    
    
	@FXML
	void addI(ActionEvent event) throws IOException {
    	    	
        JTextField textField1 = new JTextField();
        JTextField textField2 = new JTextField();
        JRadioButton button1 = new JRadioButton("ml");
        JRadioButton button2 = new JRadioButton("g");
        JRadioButton button3 = new JRadioButton("(none)");
        ButtonGroup grp = new ButtonGroup();
        grp.add(button1);
        grp.add(button2);
        grp.add(button3);

        Object[] inputFields = {"Insert ingredient:", textField1,
        						"Insert quantity:", textField2,
        						"Select unity of measurement", button1, button2, button3};
        
        Component f = null;

        int option = JOptionPane.showConfirmDialog(f, inputFields, "Add new ingredient", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
        	
        	CheckIngredientBean newIngBean = new CheckIngredientBean(textField1.getText(), textField2.getText());
        	
        	Component g = null;
        	
        	try {
        		contr.insertedIngredientIsValid(newIngBean);
        	} catch (NumberFormatException e) {
        		JOptionPane.showMessageDialog(g, "Ingredient's quantity must be a number.");
        		return;
        	} catch (StringIsEmptyException e) {
        		JOptionPane.showMessageDialog(g, e.getMessage());
        		return;
        	}
        	if(!(button1.isSelected()||button2.isSelected()||button3.isSelected())) {
    			JOptionPane.showMessageDialog(g, "Ingredient's unity of measurement is required.");
    			return;
    		}
        	
        	String n = textField1.getText();
        	float q = Float.parseFloat(textField2.getText());
            int t;
            if(button1.isSelected()) {
            	t = 2; //ml
            }
            else if(button2.isSelected()) {
            	t = 1; //g
            }
            else {
            	t = 0; //none (float)
            }
             
        	this.newCBean.getRecipe().addIngredientBean(n, q, t);
            updateIngredientListView();
        
        }

    }
    
   
    @FXML
    void removeI(ActionEvent event) throws IOException {
    	
    	int selectedIng = addTagsL.getSelectionModel().getSelectedIndex();
    	IngredientBean i = this.newCBean.getRecipe().getIngredients().get(selectedIng+1);
    	this.newCBean.getRecipe().removeIngredientBean(i);
    	updateIngredientListView();
    	
    }
    
    
    public void updateIngredientListView() {
    	int x = this.newCBean.getRecipe().getIngredients().size();
        ObservableList<String> l = FXCollections.observableArrayList();
        List<IngredientBean> a1 = this.newCBean.getRecipe().getIngredients();
        for(int m = 0; m < x; m++) {
        	String s = "";
        	String s1 = a1.get(m).getName();
        	String s2 = Float.toString(a1.get(m).getQuantity());
        	int i3 = a1.get(m).getType();
        	if(i3 == 0) {
        		s += s1+" ("+s2+")\n";
        	}
        	else if(i3 == 1) {
        		String s3 = "g";
            	s += s1+" ("+s2+" "+s3+")\n";
        	}
        	else {
        		String s3 = "ml";
            	s += s1+" ("+s2+" "+s3+")\n";
        	}
        	l.add(s);
        }
    	this.addIngredients.setItems(l);
    	boolean b = false;
    	if(l.isEmpty()) {
    		b = true;
    	}
		this.removeIngr.setDisable(b);

    }
    
   
    @FXML
	void addT(ActionEvent event) throws IOException {
		
		String t = JOptionPane.showInputDialog("Insert tag: ");
		CheckTagBean newTagBean = new CheckTagBean(t);
		try {
			contr.insertedTagIsValid(newTagBean);
		} catch (StringIsEmptyException e) {
			Component g = null;
			JOptionPane.showMessageDialog(g, e.getMessage());
			return;
		}
		
		this.newCBean.addTagBean(t);
		updateTagListView();	

	}
    
    
    @FXML
    void removeT(ActionEvent event) throws IOException {
    	
    	int selectedTag = addTagsL.getSelectionModel().getSelectedIndex();
    	
    	this.newCBean.removeTagBean(selectedTag);
    	
    	updateTagListView();
    	
    }
    
    
    public void updateTagListView() {
    	int x = this.newCBean.getTags().size();
    	ObservableList<String> l = FXCollections.observableArrayList();
        for(int m = 0; m < x; m++) {
  			String s = this.newCBean.getTags().get(m).getTagName();
        	l.add(s);
        }
    	this.addTagsL.setItems(l);
    	boolean b = false;
    	if(l.isEmpty()) {
    		b = true;
    	}
    	this.removeTags.setDisable(b);
    }
    
    
    @FXML
    public void nextImage(ActionEvent event) throws IOException {

    	int c = this.getCount();
    	this.setCount(c+1);
    	Integer newC;
    	if(this.getCount() == 40) {
    		this.setCount(0);
    		newC = 0;
    	}
    	else {
    		newC = this.getCount();
    	}

	    image.setImage(new Image(this.getImagesList().get(newC)));

    }
    
    
    @FXML
    public void previousImage(ActionEvent event) throws IOException {

    	int c = this.getCount();
    	this.setCount(c-1);
    	Integer newC;
    	if(this.getCount() == -1) {
    		this.setCount(39);
    		newC = 39;
    	}
    	else {
    		newC = this.getCount();
    	}

	    image.setImage(new Image(this.getImagesList().get(newC)));

    }
    
	
	@FXML
	void done(ActionEvent event) throws IOException {
		
    	this.newCBean.setName(addDrinkName.getText());
    	this.newCBean.getRecipe().setProcedure(addProcedure.getText());
    	this.newCBean.setImage(this.getImagesList().get(getCount()));
    	
    	try {
    		contr.insertedCocktailIsValid(newCBean);
    	} catch (StringIsEmptyException | IngredientListIsEmptyException e) {
    		this.errMsg.setText(e.getMessage());
    		return;
    	} 
		
    	// Tags are not required
				
		contr.newCocktailObject(newCBean);
    	Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleHomepage.fxml"));
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new HomepageViewController());
		Switch.switchPage(event, p);
		
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public List<String> getImagesList() {
		return imagesList;
	}


	public void addToImagesList(String i) {
		this.imagesList.add(i);
	}
}