package client.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import model.board.Board;
import model.exceptions.XMLFileException;
import model.player.Player;
import model.Configuration;

public class GameProperty {
	/**
	 * This is the list of the available colors for each game. 
	 * Each player have a color assigned to himself to distinguish from the others.
	 */
	private static List<Color> playersColors = new LinkedList<Color>(Arrays.asList(Color.ROYALBLUE, Color.CRIMSON, Color.GREEN,  Color.YELLOW,
			Color.PURPLE, Color.WHITE, Color.ORANGE, Color.GRAY, Color.AQUA, Color.BROWN));

	private ObservableList<PlayerProperty> players = FXCollections.observableArrayList();
	private SimpleMap map;
	private int myIndex;
	private Configuration config;

	public void initMap(int choosen) {
		try {
			Board b = new Board(config, choosen);
			this.map = new SimpleMap(b);
		} catch (XMLFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		myIndex = players.size() - 1;
		for (Player p : players) {
			playerJoined(p);
		}
	}

	public void updatePlayer(Player p, int index) {
		players.get(index).setAllButPermissions(p);
	}

	public void playerJoined(Player p) {
		PlayerProperty newPlayer = new PlayerProperty().setAllButPermissions(p);
		newPlayer.setColor(playersColors.remove(0));
		players.add(newPlayer);
	}
	
	public void setConfiguration(Configuration config){
		this.config=config;
	}
	
	public Configuration getConfiguration(){
		return config;
	}
}
