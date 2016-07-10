package client.gui.view;

import java.io.IOException;

import client.gui.control.MainApp;
import client.gui.model.PlayerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RoomController {
	
	@FXML private TableView<PlayerProperty> playerTable;
	@FXML private TableColumn<PlayerProperty, String> playerColumn;
		
	/**
	 * set everything for this stage
	 */
	public void setAll(MainApp mainApp) throws IOException{
		this.playerTable.setItems(mainApp.getLocalModel().getPlayers());
		playerColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
	}
}
