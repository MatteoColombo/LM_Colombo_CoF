package client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.player.Player;

public class GameProperty {

	private ObservableList<PlayerProperty> players = FXCollections.observableArrayList();
	
	public ObservableList<PlayerProperty> getPlayers() {
		return this.players;
	}
	
	public void addPlayer(Player player) {
		players.add(new PlayerProperty().setAll(player));
	}
	
	public void setPlayer(Player player, int index) {
		players.get(index).setAllButPermissions(player);
	}
}
