package fx.view;

import java.io.IOException;

import fx.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RoomController {
	
	@FXML
	private TableView<StringProperty> clientTable;
	
	@FXML
	private TableColumn<StringProperty, String> clientColumn;
	
    private ObservableList<StringProperty> clients;
	
	public RoomController() {
	}
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) throws IOException{
		this.mainApp = mainApp;
	}
	
	@FXML
	private void handleDisconnect() {
		mainApp.showLogin();
	}
	
	@FXML
	private void handleAddClient() {
		this.clients.add(new SimpleStringProperty("test"));
	}
	// TODO more stuff
	// update list when someone connect
}
