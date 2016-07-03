package client.gui.view;

import java.util.List;
import java.util.stream.Collectors;

import client.gui.control.MainApp;
import client.gui.model.PlayerProperty;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.model.player.Player;


public class ResultsController {

	@FXML
	private TableView<PlayerProperty> resultsTable;
	@FXML
	private TableColumn<PlayerProperty, String> playerColumn;
	@FXML
	private TableColumn<PlayerProperty, Integer> victoryColumn;
	@FXML
	private TableColumn<PlayerProperty, Integer> assistantColumn;
	@FXML
	private TableColumn<PlayerProperty, Integer> politicColumn;

	public void setResults(List<Player> players) {	
		
		ObservableList<PlayerProperty> playersProperty = FXCollections.observableArrayList();
		
		// wrap the players  into playerproperty classes
		for(Player player: players) {
			if(player.getVictoryPoints().getAmount() >= 0) {
				playersProperty.add(new PlayerProperty().setAllButPermissions(player));
			}
		}
	
		resultsTable.setItems(playersProperty);
		playerColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
		victoryColumn.setCellValueFactory(cell -> cell.getValue().victoryProperty().asObject());
		assistantColumn.setCellValueFactory(cell -> cell.getValue().assistantsProperty().asObject());
		politicColumn.setCellValueFactory(cell -> Bindings.size(cell.getValue().getPoliticCards()).asObject());		

	}
}
