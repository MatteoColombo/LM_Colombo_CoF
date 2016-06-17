package client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.board.Board;

public class GameProperty {
	
	private ObservableList<PlayerProperty> players = FXCollections.observableArrayList();
	private SimpleMap map;
	private int myIndex;
	
	public PlayerProperty getMyPlayerData() {
		return players.get(myIndex);
	}
	
	public ObservableList<PlayerProperty> getPlayers() {
		return this.players;
	}
	
	public void setMap(Board board) {
		this.map = new SimpleMap(board);
	}
	
	public SimpleMap getMap() {
		return this.map;
	}
	
	public int getMyIndex() {
		return myIndex;
	}
	
	public void setMyIndex(int i) {
		myIndex = i;
	}
}
