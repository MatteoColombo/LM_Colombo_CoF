package client.gui.view;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import client.gui.control.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import server.model.configuration.ConfigurationErrorException;
import server.model.configuration.XMLFileException;

public class ConfigGameController {

	@FXML private TableView<StringProperty> mapTable;
	@FXML private TableColumn<StringProperty, String> mapColumn;
	@FXML private TextField playersNumber;
	@FXML private Label maxLabel;
	
	private ObservableList<StringProperty> mapList;
	
	private MainApp mainApp;
	
	@FXML 
	private void initialize() {
		mapColumn.setCellValueFactory(cell -> cell.getValue());
		Collection.addNumericRestriction(playersNumber);
	}

	/**
	 * set the main application
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	/**
	 * set the list of available maps
	 */
	public void setMapList(List<String> maps) {
		mapList = FXCollections.observableArrayList();
		for(String map: maps) {
			Path p = Paths.get(map);
			mapList.add(new SimpleStringProperty(p.getFileName().toString()));
		}
		mapTable.setItems(mapList);
	}
	
	@FXML 
	private void handlePlay() throws IOException, ConfigurationErrorException, XMLFileException {
		if(playersNumber.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Configuration error");
			alert.setHeaderText("No players number");
			alert.setContentText("Please insert how much player you wish to play with");
			alert.show();
		} else if(Integer.parseInt(playersNumber.getText()) < 2 || 
				  Integer.parseInt(playersNumber.getText()) > 10) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Configuration error");
			alert.setHeaderText("Invalid player number");
			alert.setContentText("Please insert a number between 2 and 10 (included)");
			alert.show();
		} else {
			mainApp.getLocalModel().initMap(mapTable.getSelectionModel().getSelectedIndex());
			mainApp.sendMsg("manual");
			mainApp.sendMsg(playersNumber.getText());
			mainApp.sendMsg(String.valueOf(mapTable.getSelectionModel().getSelectedIndex()+1).toString());
		}
	}
	
	@FXML
	private void handlePlayRandom() {
		mainApp.sendMsg("random");
	}
}
