package fx.view;

import java.io.IOException;

import client.model.PlayerProperty;
import fx.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RoomController {
	
	@FXML private TableView<PlayerProperty> playerTable;
	@FXML private TableColumn<PlayerProperty, String> playerColumn;
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) throws IOException{
		this.mainApp = mainApp;
		this.playerTable.setItems(mainApp.getLocalModel().getPlayers());
		playerColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
	}
	
	@FXML private void handleDisconnect() {
		// TODO complete
		mainApp.showLogin();
	}
}
