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
import logic.controller.SponsorController;
import logic.exception.PaymentNotAcceptedException;

public class PrePayViewController implements Initializable {
	
	@FXML
    private ImageView paypalImage;

    @FXML
    private Button paypalButton;

    @FXML
    private ImageView messageImage;

    @FXML
    private Label messPrice;
    
    @FXML
    private Label success;
    
    @FXML
    private Button backPay;
    
    @FXML
	private ImageView imgBan;
    
    @FXML
    private Label specific;
	
	private String s1 = "Great! Now you are in the spotlight.\nYour sponsorship will automatically end after your choosed deadline.\nDon't worry, there is no automatic rinovation.\nYou can always check the deadline in your 'Sponsor' page.";
	private String s2 = "We're so sorry, but your payment is not accepted.\nGo check your paypal credit and try again later.\nHope to see you soon!";

    @FXML
    void backPay(ActionEvent event) throws IOException {
    	Parent p = FXMLLoader.load(getClass().getResource("/logic/sample/SampleHomepage.fxml"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new HomepageViewController());
			
		Switch.switchPage(event, p).show();
    }
    
    @FXML
    void paying(ActionEvent event) throws InterruptedException, FileNotFoundException {
    	backPay.setText("Go back on\n homepage");
    	SponsorController controller = SponsorController.getInstance();
    	boolean p = controller.paymentProbab();
    	try {
        	if(p) {
        		
        		/*ora va a salvare nel db la promozione registrata.
        		 * si prende scadenza, username, tipo sponsor.
        		 */
        		if(controller.saveSponsor()) {
        			messageImage.setImage(new Image("logic/image/tick.png"));
            		success.setText("Payment accepted");
            		specific.setText(s1);
        		}
        		else {
        			messageImage.setImage(new Image("logic/image/report.png"));
            		success.setText("Something goes wrong.\nPayment not accepted.");
            		specific.setText(s2);
        		}
        	}
        	else {
        		throw new PaymentNotAcceptedException("Something goes wrong.\nPayment not accepted.");
        	}
    	} catch (PaymentNotAcceptedException e) {
    		messageImage.setImage(new Image("logic/image/report.png"));
    		success.setText(e.getMessage());
    		specific.setText(s2);
    	}
    	paypalButton.setDisable(true);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		double price = 0; 
		imgBan.setImage(new Image("logic/image/ban.JPG"));
		SponsorController controller = SponsorController.getInstance();
		price = controller.getBean().getPrice();
		paypalButton.setDisable(false);
		messPrice.setText(messPrice.getText() + " " + price);
	}

}
