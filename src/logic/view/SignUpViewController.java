package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.bean.SignUpBean;
import logic.controller.SignUpController;
import logic.model.Address;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class SignUpViewController implements Initializable, MapComponentInitializedListener {
	
	private GeocodingService geocodingService;
	private static Double latitude;
	private static Double longitude;
	private static final String KEY = "AIzaSyBa9OhzI_r8hilU0D9TzQf44mipf2W63pY";
	
	@FXML
	private GoogleMapView mapView;
	
	@FXML
    private TextField username;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField address;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button signUpButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private CheckBox checkBar;
    
    @FXML
    private ChoiceBox<String> profileImage;
    
    @FXML
    private Label messageError;
    
    @FXML
    private Button loginButton;
    
    @FXML
    void loginAction(ActionEvent event) throws IOException {
    	Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleLogin.fxml"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new LoginViewController());
		
		Switch.switchPage(event, p).show();
    }

    @FXML
    void cancelAction(ActionEvent event) throws IOException {
    	Parent p1 = FXMLLoader.load(getClass().getResource("/logic/sample/SampleLogin.fxml"));
		
		FXMLLoader loader1 = new FXMLLoader();
		loader1.setController(new LoginViewController());
		
		Switch.switchPage(event, p1).show();
    }
    
    @FXML
    void signUpAction(ActionEvent event) {
    	SignUpController controller = SignUpController.getInstance();
    	SignUpBean b = controller.getBean();
    	b.setUsernameSign(username.getText());
    	boolean exist;
    	exist = controller.findUsername();
    	
    	if(!exist) {
    		//go on and save other data in bean and after save other data in db
    		if((name.getText().equals(""))||(surname.getText().equals(""))||( passwordField.getText().equals(""))) {
    			//data error
    			messageError.setText("ERROR. Fill all fields.");
    		}
    		else {
    			//all data inside
    			b.setNameSign(name.getText());
        		b.setSurnameSign(surname.getText());
        		b.setPasswordSign(passwordField.getText());
        		int isBar;
        		if(checkBar.isSelected()) {
        			isBar = 1;
        		}
        		else {
        			isBar = 0;
        		}
        		b.setIsBarSign(isBar);
        		String image = profileImage.getSelectionModel().getSelectedItem();
        		String finalImage;
        		finalImage = chooseProfileImage(image);
        		b.setImageSign(finalImage);
        		geocodingService.geocode(address.getText(), new GeocodingServiceCallback() {
    				@Override
    				public void geocodedResultsReceived(GeocodingResult[] results, GeocoderStatus status) {
    					 if( status == GeocoderStatus.ZERO_RESULTS) {
    			            	// Not found
    			            } else {
    			            	SignUpViewController.setLatitude(results[0].getGeometry().getLocation().getLatitude());
    			                SignUpViewController.setLongitude(results[0].getGeometry().getLocation().getLongitude());
    			                Double lat;
    			                Double lon;
    			                lat = SignUpViewController.getLatitude();
    			                lon =  SignUpViewController.getLongitude();
    			                Address a = new Address(lat, lon, address.getText());
    			                b.setAddressSign(a);
    			                controller.saveNewUser();
    			        		messageError.setText("OK DONE");
    			        		loginButton.setVisible(true);
    			               
    			            }    	
    				}
    			});
    		}
    		
    	}
    	else {
    		//already exists
    		messageError.setText("ERROR. This username is already used.");
    	}
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	this.mapView.setKey(KEY);
		this.mapView.addMapInializedListener(this);	
		this.mapView.setVisible(false); 
		
    	
    	profileImage.setItems(FXCollections.observableArrayList("woman","man","not specified"));
    	messageError.setText("");
    	loginButton.setVisible(false);
    	
    	
    }

	public static Double getLatitude() {
		return latitude;
	}

	public static void setLatitude(Double latitude) {
		SignUpViewController.latitude = latitude;
	}

	public static Double getLongitude() {
		return longitude;
	}

	public static void setLongitude(Double longitude) {
		SignUpViewController.longitude = longitude;
	}

	@Override
	public void mapInitialized() {
		
		geocodingService = new GeocodingService();		
		MapOptions mapOptions = new MapOptions();
		LatLong latLong;
		latLong = new LatLong(0.0, 0.0);
		setLatitude(0.0);
    	setLongitude(0.0);
    	
		mapOptions.center(latLong)
			.mapType(MapTypeIdEnum.ROADMAP) 
			.overviewMapControl(true)
			.panControl(true)
			.rotateControl(true)
			.scaleControl(true)
			.streetViewControl(false)
			.zoomControl(true)
			.zoom(12);
		
		mapView.createMap(mapOptions);
	}
	
	public String chooseProfileImage(String image) {
		if(image == null) {
			image="null";
		}
		if(image.equals("woman")) {
			image = "image/woman.png";
		}
		else if(image.equals("man")) {
			image = "image/man.png";
		}
		else {
			image = "image/null.png";
		}
		return image;
	}
    
}
