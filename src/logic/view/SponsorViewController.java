package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import logic.bean.SponsorBean;
import logic.controller.LoginController;
import logic.controller.SponsorController;

public class SponsorViewController implements Initializable {
	
	@FXML
    private Button confirm;
	
	@FXML
    private Button preview;

	@FXML
	private ComboBox<String> boxChoose;

	@FXML
    private ComboBox<String> boxTime;
	
	@FXML
    private Label choosing;

	@FXML
    private Label timing;
	
	@FXML
    private Label priceFinal;
	
	@FXML
    private Label titleSponsor;
	

	
	@FXML
	void time(ActionEvent event) {
		String text2 = boxTime.getSelectionModel().getSelectedItem();
    	timing.setText(text2);
    	
	}

    @FXML
    void choose(ActionEvent event) {
    	String text1 = boxChoose.getSelectionModel().getSelectedItem();
    	choosing.setText(text1);
    }
    
    @FXML
    void pricePrieview(ActionEvent event) {
    	
    	SponsorController controller = SponsorController.getInstance();
    	
    	SponsorBean bPrice = new SponsorBean();
    	bPrice.setType(choosing.getText());
    	bPrice.setTime(timing.getText());
    	
		bPrice = controller.calculatePricePreview(bPrice);
		
    	if(bPrice.getPrice() != 0) {
    		String t = String.valueOf(bPrice.getPrice());
    		priceFinal.setText(t);
    		confirm.setDisable(false);
    		
    		controller.setBean(bPrice);
    	}
    	else {
    		priceFinal.setText("INPUT NOT VALID");
    	}
    }

	
	@FXML
    void sendRequest(ActionEvent event) throws IOException {
		//dopo va alle api di paypal
		
		Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SamplePrePay.fxml"));
			
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new PrePayViewController());
			
		Switch.switchPage(event, p).show();
		
		//==========
		
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		/* 
		 * boxChoose: scelta tra promozione del profilo, dei post, o di entrambi.
		 * 				
		 * 				- solo post -> costo*1		(cod: tp=1)
		 * 				- solo profilo -> costo*1.5 (cod: tp=2)
		 * 				- entrambi -> costo*1.8		(cod: tp=3)
		 * boxTime: scelta della durata della promozione.
		 * 			5 euro/giorno. sconto del:
		 * 								0%			(cod: tm=0)
		 * 							   10%			(cod: tm=1)
		 * 							   15%			(cod: tm=2)
		 * 							   20%			(cod: tm=3)
		 * 							   30%			(cod: tm=4)
		 * 									rispettivamente per le varie durate
		*/
	
		
		
		confirm.setDisable(true);
		
		SponsorController controller = SponsorController.getInstance();
		
		String usernameSponsor = LoginController.getInstance().getBean().getUsername();
		
		SponsorBean sBean = new SponsorBean();
		sBean.setUserSponsor(usernameSponsor);
		sBean = controller.searchExistingS(sBean);
		
		if( sBean.getTimeSponsor() != null) {
			boxChoose.setDisable(true);
			boxTime.setDisable(true);
			confirm.setDisable(true);
			preview.setDisable(true);
			titleSponsor.setText("Your sponsor is still in activity. Will end in " + sBean.getTimeSponsor());
		}
		
		else {
			titleSponsor.setText("Sponsor your profile and your posts.");
			boxChoose.setItems(FXCollections.observableArrayList("Profile","Post","Both"));
			boxTime.setItems(FXCollections.observableArrayList("1 month","2 months (-10%)","3 months (-15%)", "6 months (-20%)", "1 year (-30%)"));
			
		}
	}

}
