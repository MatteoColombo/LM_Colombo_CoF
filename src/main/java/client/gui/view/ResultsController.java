package client.gui.view;

import client.gui.control.MainApp;
import client.gui.model.PlayerProperty;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class ResultsController {

	@FXML
	private TableView<PlayerProperty> resultsTable;
	@FXML
	private TableColumn<PlayerProperty, String> playerColumn;
	@FXML
	private TableColumn<PlayerProperty, String> victoryColumn;
	@FXML
	private TableColumn<PlayerProperty, String> assistantColumn;
	@FXML
	private TableColumn<PlayerProperty, String> politicColumn;
	
	private MainApp mainApp;
	
	public void setAll(MainApp mainApp) {
		this.mainApp = mainApp;
		setResults();
	}
	
	private void setResults() {
		resultsTable.setItems(mainApp.getLocalModel().getPlayers());
		playerColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
		victoryColumn.setCellValueFactory(cell -> cell.getValue().victoryProperty().asString());
		assistantColumn.setCellValueFactory(cell -> cell.getValue().assistantsProperty().asString());
		politicColumn.setCellValueFactory(cell -> Bindings.size(cell.getValue().getPoliticCards()).asString());		

		resultsTable.getSortOrder().setAll(victoryColumn, assistantColumn, politicColumn);
	}
}
