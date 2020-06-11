package logic.view;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Switch {
	
	private Switch() {
		//do nothing ma sonarcloud lo vuole e vabbe lo facciamo
	}
	
	public static Stage switchPage(ActionEvent event, Parent p) {
		Object eventSource = event.getSource(); 
		Node sourceAsNode = (Node) eventSource ;
		Scene oldScene = sourceAsNode.getScene();
		Window window = oldScene.getWindow();
		Stage stage = (Stage) window ;
		Scene scene = new Scene(p);
		stage.setResizable(false);
		stage.setScene(scene);
		return stage;
	}

}
