package logic.view;

import java.awt.Component;
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


import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import logic.bean.RadiusBean;
import logic.bean.BarUserBean;
import logic.bean.FindUserBean;
import logic.controller.FindBarUserController;
import logic.exception.StringIsEmptyException;


public class FindBarUserViewController implements Initializable, 
												MapComponentInitializedListener {
	
	private FindBarUserController barController;
	
    private LatLong userLatLon = null;
    private JTextField txtRadius = new JTextField(0);
    private JTextField txtNewAddress = new JTextField("");
	private JRadioButton metersBtn = new JRadioButton("Kilometers");
	private JRadioButton milesBtn = new JRadioButton("Miles");
	private JRadioButton byUserDefPosBtn = new JRadioButton("User default position");
	private JRadioButton byOthPosBtn = new JRadioButton("Insert new Address");
	private JRadioButton nameBtn = new JRadioButton("Name");
	private JRadioButton locationBtn = new JRadioButton("Location");
	private ButtonGroup	btnGroupNL = new ButtonGroup();
	private ButtonGroup btnGroupRadio = new ButtonGroup();
	private ButtonGroup btnGroupCheck = new ButtonGroup();
    private GeocodingService geocodingService;
    private LatLong latLong = null;
	private static final String KEY = "AIzaSyBa9OhzI_r8hilU0D9TzQf44mipf2W63pY";
	
	
	@FXML
	private Button btnBack;
	
	@FXML
	private Button btnSearch;
	
	@FXML
	private Button btnVisit;
	
	@FXML
	private GoogleMapView mapView;
	private GoogleMap map;
	
	@FXML
	private ListView<String> userListByName;
	ObservableList<String> data = FXCollections.observableArrayList();
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.mapView.setKey(KEY);
		this.mapView.addMapInializedListener(this);		
		
		
		userListByName.setItems(data);
		btnVisit.setDisable(true);	
		btnSearch.setDisable(true);
		this.barController = FindBarUserController.getInstance();
		
	}

	@Override
	public void mapInitialized() {
		
		Double[] s = barController.getLatiLong();
		geocodingService = new GeocodingService();		
		MapOptions mapOptions = new MapOptions();
		this.userLatLon = new LatLong(s[0], s[1]);

		mapOptions.center(userLatLon)
			.mapType(MapTypeIdEnum.ROADMAP) 
			.overviewMapControl(true)
			.panControl(true)
			.rotateControl(true)
			.scaleControl(true)
			.streetViewControl(false)
			.zoomControl(true)
			.zoom(12);
		
		map = mapView.createMap(mapOptions);
		
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(userLatLon);
		Marker marker = new Marker(markerOptions);
		map.addMarker(marker);
		InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
	    infoWindowOptions.content(" YOU ");
        InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
        infoWindow.open(map, marker);
        
        btnSearch.setDisable(false);  
        
        
	}
	
	@FXML
	private void goBack(ActionEvent event) throws IOException {
		
		Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleHomepage.fxml"));
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new HomepageViewController());
		Switch.switchPage(event, p).show();
	}
	
	@FXML
	private void searchBar(ActionEvent event) throws IOException {
		this.data = FXCollections.observableArrayList(); 
		btnGroupNL.add(nameBtn);
		btnGroupNL.add(locationBtn); 
		nameBtn.setSelected(true); 
		
		Object[] possibilities = {"Search by: ", nameBtn, locationBtn};
		Frame frame = new Frame();
		int searchMethod = JOptionPane.showConfirmDialog(frame, possibilities, 
				"Search bar", JOptionPane.OK_CANCEL_OPTION);
		
		if (searchMethod == JOptionPane.OK_OPTION) {
			if (nameBtn.isSelected()) {
				searchByName(event);
		
			} else if (locationBtn.isSelected()) {
				searchByLocation();
				 
			} 
		}else {
			frame.dispose();
		}
		
	}

	
	private void searchByName(ActionEvent event) throws IOException {
		JTextField txtName = new JTextField();
		JFrame frName = new JFrame();
		Object[] inputName = new Object[] {"", txtName};
		txtName.setText("");
					
		int optionName = JOptionPane.showConfirmDialog(frName, inputName, "Search Bar with name: ", 
				JOptionPane.OK_CANCEL_OPTION);
		
		if (optionName == JOptionPane.OK_OPTION) {
			
			FindUserBean userBean = new FindUserBean();
			userBean.setUsername(txtName.getText());
			
			try {
				if(barController.isInsertedUserUsernameValid(userBean)) {
					barController.fillUpProfileByName(userBean);
					Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleUserPage.fxml"));
					FXMLLoader loader = new FXMLLoader();
					loader.setController(new UserViewController());
					Switch.switchPage(event, p).show(); 
					
				}else {
					Frame f = new Frame();
					JOptionPane.showMessageDialog(f,txtName.getText()
							+ "  not found" + "\n" +"Try again",
							null, JOptionPane.OK_OPTION);	
				}
			} catch (StringIsEmptyException e) {
				Frame j = new Frame();
				JOptionPane.showMessageDialog(j,"Write something", null , JOptionPane.OK_OPTION);	
			}		
		} else {
			frName.dispose();
		}
	}
	
	
	private void searchByLocation() {
		
		this.byUserDefPosBtn.setSelected(true); 
		this.metersBtn.setSelected(true);
		this.btnGroupRadio.add(milesBtn);
		this.btnGroupRadio.add(metersBtn);
		this.btnGroupCheck.add(byUserDefPosBtn);
		this.btnGroupCheck.add(byOthPosBtn);
		this.txtRadius.setText("");
		this.txtNewAddress.setText("");
		
        Object[] inputLocation = {"Enter the maximum ray: ", txtRadius, "in: ", metersBtn, milesBtn,
        		"" , "-------------------------", 
        		byUserDefPosBtn, byOthPosBtn, "Enter new Address", txtNewAddress}; 
        JFrame frLocation = new JFrame();
        
     
        int optionLocation = JOptionPane.showConfirmDialog(frLocation, inputLocation, "Choose the ray",
        		    	JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
        if (optionLocation == JOptionPane.OK_OPTION) {
        	//scegliere il bottone check
        	if (byUserDefPosBtn.isSelected()) {
				chooseMetersMiles(userLatLon);
			} else if (byOthPosBtn.isSelected()) {
				String address = txtNewAddress.getText();
				geocodingService.geocode(address, new GeocodingServiceCallback() {
					
					@Override
					public void geocodedResultsReceived(GeocodingResult[] results, GeocoderStatus status) {
						 
				            if( status == GeocoderStatus.ZERO_RESULTS) {
				            	Frame af = new Frame();
								JOptionPane.showMessageDialog(af,"No result found", null , JOptionPane.OK_OPTION);

				            } else if( results.length > 1 ) {
				            	Frame bf = new Frame();
								JOptionPane.showMessageDialog(bf,"Multiple results found ", null , JOptionPane.OK_OPTION);
				                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), 
				                		results[0].getGeometry().getLocation().getLongitude());
				                chooseMetersMiles(latLong);
				            } else {
				                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), 
				                		results[0].getGeometry().getLocation().getLongitude());
				                chooseMetersMiles(latLong);
				            }    		
					}
				});
			}
		} else {
			frLocation.dispose();
		}
	}
	
	@FXML
	private void visitBar(ActionEvent event) throws IOException {
		BarUserBean bBean = new BarUserBean();
		bBean.setBarUsername(userListByName.getSelectionModel().getSelectedItem().toString());
		barController.fillUpProfile(bBean);
		Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleUserPage.fxml"));
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new UserViewController());
		Switch.switchPage(event, p).show();
	}
	
	private void chooseMetersMiles(LatLong newPosition) {
		LatLong newPos = newPosition;
		RadiusBean radiusBean = new RadiusBean(txtRadius.getText());
    	Component g = null;
    	try {
			barController.insertedRadiusIsValid(radiusBean);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(g, "Radius must be an integer");
			return;
		}
    	
    	if (metersBtn.isSelected()) {
    		Double distanceMeters = Double.parseDouble(txtRadius.getText());
    		Double meters = distanceMeters*1000;
    		showOnMapMetersMiles(meters, newPos);
		} else if(milesBtn.isSelected()) {
			Double distanceMiles = Double.parseDouble(txtRadius.getText());
			Double miles = distanceMiles/0.000621371;
			showOnMapMetersMiles(miles, newPos);
		}
	}
	
	private void showOnMapMetersMiles(Double radius, LatLong pos) {
		
		List<BarUserBean> bar = barController.getBars();
		
		map.clearMarkers();
		
    	for (BarUserBean ub : bar) {

    		LatLong barLatLong = new LatLong(ub.getLatitudine(), ub.getLongitudine());
    		
    		if (pos.distanceFrom(barLatLong) <= radius) {
					
    			MarkerOptions markerOptions2 = new MarkerOptions();
    			markerOptions2.position(barLatLong);
    			Marker marker2 = new Marker(markerOptions2);
    			map.addMarker(marker2);
				InfoWindowOptions infoWindowOptions2 = new InfoWindowOptions();
    			infoWindowOptions2.content("<h3>" + ub.getBarUsername() + "<h5>" + ub.getBarAddress() + "<br>" );
    			InfoWindow infoWindow2 = new InfoWindow(infoWindowOptions2);
    			infoWindow2.open(map, marker2);
    			
    			data.add(ub.getBarUsername());
    			
    		}
    		
    		userListByName.setItems(data);
			map.setZoom(10);
    		userListByName.setOnMouseClicked(new  EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if (!data.isEmpty()  ) {
						btnVisit.setDisable(false);
	
					}					
				}
			});
    		
    		MarkerOptions markerOptions = new MarkerOptions();
    		markerOptions.position(pos);
    		Marker marker = new Marker(markerOptions);
    		map.addMarker(marker);
    		InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
    	    infoWindowOptions.content(" YOU ");
            InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
            infoWindow.open(map, marker);
		}			
	}
}
