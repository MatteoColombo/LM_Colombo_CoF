package client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameProperty {
	
	private ObservableList<PlayerProperty> players = FXCollections.observableArrayList();
	private int myIndex;
	
	public int getMyIndex() {
		return myIndex;
	}
	
	public void setMyIndex(int i) {
		myIndex = i;
	}
	
	public ObservableList<PlayerProperty> getPlayers() {
		return this.players;
	}
	
	public PlayerProperty getMyPlayerData() {
		return players.get(myIndex);
	}
}
