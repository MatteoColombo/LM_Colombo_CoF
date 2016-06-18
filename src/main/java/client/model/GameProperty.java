package client.model;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.board.Board;
import model.exceptions.ConfigurationErrorException;
import model.exceptions.XMLFileException;
import model.player.Player;
import model.Configuration;

public class GameProperty {
	
	private ObservableList<PlayerProperty> players = FXCollections.observableArrayList();
	private SimpleMap map;
	private int myIndex;
	
	public void initMap(int choosen) throws ConfigurationErrorException, XMLFileException  {
		{ Board b = new Board(new Configuration(), choosen);
		  this.map = new SimpleMap(b);
		}
		// TODO call the garbage collector
	}
	
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
	
	public void isYourTurn() {
		players.get(myIndex).canNotDoMainAction().set(false);
		players.get(myIndex).canNotDoSideAction().set(false);
	}
	
	public void endOfTurn() {
		players.get(myIndex).canNotDoMainAction().set(true);
		players.get(myIndex).canNotDoSideAction().set(true);
	}
	
	public void setAllPlayers(List<Player> players) {
		this.players.clear();
		myIndex = players.size()-1;
		for(Player p: players) {
			playerJoined(p);
		}
	}
	
	public void updatePlayer(Player p, int index) {
		players.get(index).setAllButPermissions(p);
	}
	
	public void playerJoined(Player p) {
		players.add(new PlayerProperty().setAllButPermissions(p));
	}
	
}
