package client.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import model.board.Board;
import model.board.council.Council;
import model.exceptions.XMLFileException;
import model.player.Player;
import model.reward.Reward;
import model.Configuration;

public class GameProperty implements ModelInterface {
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

	@Override
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

	@Override
	public void setMap(Board board) {
		this.map = new SimpleMap(board);
	}

	public SimpleMap getMap() {
		return this.map;
	}

	@Override
	public int getMyIndex() {
		return myIndex;
	}

	@Override
	public void isYourTurn() {
		players.get(myIndex).canNotDoMainAction().set(false);
		players.get(myIndex).canNotDoSideAction().set(false);
	}

	@Override
	public void yourTurnEnded() {
		players.get(myIndex).canNotDoMainAction().set(true);
		players.get(myIndex).canNotDoSideAction().set(true);
	}

	@Override
	public void setAllPlayers(List<Player> players) {
		this.players.clear();
		myIndex = players.size() - 1;
		for (Player p : players) {
			playerJoined(p);
		}
	}

	@Override
	public void updatePlayer(Player p, int index) {
		players.get(index).setAllButPermissions(p);
	}

	@Override
	public void playerJoined(Player p) {
		PlayerProperty newPlayer = new PlayerProperty().setAllButPermissions(p);
		newPlayer.setColor(playersColors.remove(0));
		players.add(newPlayer);
	}
	
	@Override
	public void setConfiguration(Configuration config){
		this.config=config;
	}
	
	@Override
	public Configuration getConfiguration(){
		return config;
	}


	@Override
	public void setCouncil(Council council, int index) {
		this.getMap().setCouncil(council, index);
	}

	@Override
	public void setBonus(List<Reward> bonus) {
		this.getMap().setCityRewards(bonus);
	}

}
