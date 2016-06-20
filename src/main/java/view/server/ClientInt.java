package view.server;

import java.io.IOException;
import java.util.List;

import control.Controller;
import model.exceptions.IllegalActionException;
import model.market.OnSaleItem;
import model.player.Player;
import model.reward.Reward;

/**
 * This is the Client Interface and it represents a generic connection of a
 * client to the server
 * 
 * @author Matteo Colombo
 *
 */
public interface ClientInt {

	/**
	 * Sets the controller so that the view can contact it
	 * 
	 * @param controller
	 *            the server side game controller
	 */
	public void setController(Controller controller);

	public void askPlayerWhatActionToDo() throws IOException;

	/**
	 * Return the name of the player
	 * 
	 * @return a String, the name of the player
	 */
	public String getName();

	/**
	 * Asks to the initial client of a game to choose a map and the max number
	 * of players. The max number of players can't be more than a limit imposed
	 * by the game config
	 * 
	 * @param maps
	 *            a list of map names
	 * @param maxNumberOfPlayers
	 *            the max number of players by configuration
	 * @throws IOException
	 */
	public void askConfiguration(int maxNumberOfPlayers) throws IOException;

	/**
	 * Closes the connection
	 */
	public void close();

	/**
	 * Asks to the client its name and saves it in an attribute
	 * 
	 * @throws IOException
	 */
	public void askPlayerName() throws IOException;

	/**
	 * Notifies the player that the action that he choose is invalid for some
	 * reason
	 */
	public void notifyIllegalAction(IllegalActionException exception);

	/**
	 * Returns if the client is still connected
	 * 
	 * @return true if connected, false otherwise
	 */
	public boolean isConnected();

	public void notifyGameLoading(int choosenMap) throws IOException;

	public void notifyGameStarted() throws IOException;

	public void notifyYourTurn() throws IOException;
	
	public void sendPlayersList(List<Player> players) throws IOException;
	
	public void notifyPlayerJoined(Player player) throws IOException;

	public void notifyAnotherPlayerTurn() throws IOException;

	public void askPlayerItemToBuy(List<OnSaleItem> itemsOnSale) throws IOException;
	
	public void askWichItemToSell() throws IOException;
	
	public void updatePlayer(Player player, int index) throws IOException;
	
	public void askCityToGetNobilityReward(int citiesNumber) throws IOException;
	
	public void askSelectRewardOfPermissionCard() throws IOException;
	
	public void askSelectFreePermissionCard() throws IOException;
	
	public void sendNotifyCityBonus(List<Reward> rewards) throws IOException;
	
	
	
	//public void askPlayerWhichMerchandiseBuy(Player buyingPlayer, List<Player> allPlayers) throws IOException;

	//public boolean askPlayerConfirmation() throws IOException;
}
