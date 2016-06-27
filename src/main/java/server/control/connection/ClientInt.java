package server.control.connection;

import java.io.IOException;
import java.util.List;

import server.control.Controller;
import server.control.dialogue.Dialogue;
import server.model.action.IllegalActionException;
import server.model.market.OnSaleItem;

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

	public void notify(Dialogue dialog) throws IOException;
	

	public void askPlayerItemToBuy(List<OnSaleItem> itemsOnSale) throws IOException;
	
	public void askWichItemToSell() throws IOException;
	
	
	public void askCityToGetNobilityReward(int citiesNumber) throws IOException;
	
	public void askSelectRewardOfPermissionCard() throws IOException;
	
	public void askSelectFreePermissionCard() throws IOException;
	
	
	//public void askPlayerWhichMerchandiseBuy(Player buyingPlayer, List<Player> allPlayers) throws IOException;

	//public boolean askPlayerConfirmation() throws IOException;
}
