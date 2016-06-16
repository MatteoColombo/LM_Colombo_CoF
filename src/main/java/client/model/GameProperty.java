package client.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameProperty {
	
	private BooleanProperty canNotDoMainAction = new SimpleBooleanProperty(true);
	private BooleanProperty canNotDoSideAction = new SimpleBooleanProperty(true);
	
	private ObservableList<PlayerProperty> players = FXCollections.observableArrayList();
	private int myIndex;
	
	public void setMyIndex(int i) {
		myIndex = i;
	}
	
	public BooleanProperty canNotDoMainAction() {
		return canNotDoMainAction;
	}
	
	public BooleanProperty canNotDoSideAction() {
		return canNotDoSideAction;
	}
	
	public ObservableList<PlayerProperty> getPlayers() {
		return this.players;
	}
	
	public PlayerProperty getMyPlayerData() {
		return players.get(myIndex);
	}
}
