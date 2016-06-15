package client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameProperty {

	private ObservableList<PlayerProperty> players = FXCollections.observableArrayList();
	private int myIndex = 0;
	
	public ObservableList<PlayerProperty> getPlayers() {
		return this.players;
	}
	
	public PlayerProperty getMyPlayerData() {
		return players.get(myIndex);
	}
}
