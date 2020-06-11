package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class UserViewController implements Initializable{
	@FXML
	private ListView<String> listTodo;

	private ObservableList<String> items = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		listTodo.setItems(items);
	    items.add("First task");
	    items.add("Second task");
	} 
}