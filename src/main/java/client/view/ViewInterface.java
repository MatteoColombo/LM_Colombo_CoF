package client.view;

import java.util.List;

import client.model.GameProperty;
import model.board.Region;
import model.player.Player;
import model.reward.Reward;

public interface ViewInterface {

	public void printAskPlayersNumber(int max);

	public void printAskWhichMapToUse();

	public void printAskWhatActionToDo();

	public void printCities();

	public void printAskPlayerName();

	public void printIllegalAction(Exception e);

	public void showInitMenu();

	public void showGetConnectionType();

	public void showGame();

	public void showRoom();

	public void playerJoined(Player p);

	public void setAllPlayers(List<Player> players);

	public void updatePlayer(Player p, int index);

	public void setCityRewards(List<Reward> bonusList);

	public void isYourTurn();

	public void yourTurnEnded();

	/**
	 * this give to the caller (a Dialogue probably) the access to the local
	 * model, and update the changes done on the model in the server. The local
	 * model is implemented with java Fx Collection Classes
	 * 
	 * @return the local Model
	 */
	public GameProperty getLocalModel();

}
