package logic.view;

import java.awt.Component;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.bean.CocktailFilterBean;
import logic.bean.CocktailFilterType;
import logic.bean.NewCocktailBean;
import logic.bean.ProfileBean;
import logic.controller.CocktailPostController;
import logic.exception.PostListIsNullException;
import logic.exception.StringIsEmptyException;

public class UserPageViewController implements Initializable {
	
    ObservableList<Pane> list = FXCollections.observableArrayList();

    @FXML
    private ListView<Pane> listView;
    
    @FXML
    private ImageView userImage;

    @FXML
    private Label userLabel;

    @FXML
    private Label nameLabel;
    
    @FXML
    private Button addF;
    
    @FXML
    private Button removeF;

    @FXML
    Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
        try {
        	
        	//inizializza parte sinistra dell'user
        	String usernameProfile = ProfileBean.getUsername();
        	userLabel.setText("@"+usernameProfile);
        	nameLabel.setText(ProfileBean.getName()+" "+ProfileBean.getSurname());
        	userImage.setImage(new Image("logic/"+ProfileBean.getImage()));
        	// ============
        	
        	
        	CocktailPostController con = CocktailPostController.getInstance();
        	con.getBean().setUsername(usernameProfile);
        	con.findCocktailList();
        	this.listView.setItems(this.list);
        	this.listView.setFocusTraversable( false );

            if(con.getBean().getPostList() == null) {
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/sample/SamplePost.fxml"));
                Pane plist = loader.load();
                PostViewController controller = loader.getController();
                controller.setTitleText("NO POSTS");
                list.add(plist);
                return;
            }
            
            
            this.list.removeIf(p -> true);
            for (int j = 0; j < con.getCocktailBeans().size(); j++) {
            	 list.add(this.buildPostView(con.getCocktailBeans().get(j)));
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(ListView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PostListIsNullException e) {
			e.printStackTrace();
		}
    }
    
    
    private Pane buildPostView(NewCocktailBean cb) {
    	
    	try {
    		
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/logic/sample/SamplePost.fxml"));
        	Pane post = fxmlLoader.load();
        	PostViewController controller = fxmlLoader.getController();

        	controller.setTitleText(cb.getName());
        	controller.setLabelTextRecip(cb.getDate().toString());
        	controller.setIdCocktail(cb.getId());
        	//prendere stringa dal db che ha url dell'immagine
        	
        	String cocktailImage = "logic/" + cb.getImage();
        	controller.setImageUrl(cocktailImage);
        	return post;
    	
    	} catch (IOException ex) {
            Logger.getLogger(ListView.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


    }
    
    
    @FXML
    public void addFilter(ActionEvent event) throws IOException {
    	
    	JTextField textField1 = new JTextField();
        JRadioButton button1 = new JRadioButton("Ingredient name");
        JRadioButton button2 = new JRadioButton("Tag name");
        ButtonGroup grp1 = new ButtonGroup();
        grp1.add(button1);
        grp1.add(button2);
        JRadioButton button3 = new JRadioButton("Include");
        JRadioButton button4 = new JRadioButton("Exclude");
        ButtonGroup grp2 = new ButtonGroup();
        grp2.add(button3);
        grp2.add(button4);



        Object[] inputFields = {"Insert filter:", textField1,
        						"Filter by:", button1, button2,
        						"Filter type:", button3, button4};
        
        Component f = null;

        int option = JOptionPane.showConfirmDialog(f, inputFields, "Add filter", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
        	
        	if(!(button1.isSelected()||button2.isSelected())) {
    			JOptionPane.showMessageDialog(f, "'Filter by' is required");
    			return;
    		}
        	else if(!(button3.isSelected()||button4.isSelected())) {
    			JOptionPane.showMessageDialog(f, "'Filter type' is required");
    			return;
    		}
        	
        	CocktailFilterType type = setType(button1.isSelected(), button3.isSelected());
        	        	
        	CocktailFilterBean cfb = new CocktailFilterBean(type, textField1.getText());
        	
        	Component g = null;
        	
        	try {
        		CocktailPostController.getInstance().addFilter(cfb);
        	} catch (StringIsEmptyException e) {
        		JOptionPane.showMessageDialog(g, e.getMessage());
        		return;
        	}
        }
        
        
        this.list.removeIf(p -> true);
        
    	CocktailPostController con = CocktailPostController.getInstance();
    	con.getCocktailBeans();
    	for (int j = 0; j < con.getCocktailBeans().size(); j++) {
   	 		this.list.add(this.buildPostView(con.getCocktailBeans().get(j)));
    	}
    	
    }
    
    
    public CocktailFilterType setType(boolean b1, boolean b3) {
    	CocktailFilterType type;
    	if (b1) {
    		if (b3)
    			type = CocktailFilterType.INCLUDE_INGREDIENT;
    		else
    			type = CocktailFilterType.EXCLUDE_INGREDIENT;
    	}
    	else {
    		if (b3)
    			type = CocktailFilterType.INCLUDE_TAG;
    		else
    			type = CocktailFilterType.EXCLUDE_TAG;
    	}
    	
    	return type;
    }
    
    
    @FXML
    public void removeFilter(ActionEvent event) throws IOException {
    	CocktailPostController con = CocktailPostController.getInstance();
    	con.removeAllFilters();
    	
    	this.list.removeIf(p -> true);
        
    	for (int j = 0; j < con.getCocktailBeans().size(); j++) {
    	 		this.list.add(this.buildPostView(con.getCocktailBeans().get(j)));
     	}
    }
    
    
}
