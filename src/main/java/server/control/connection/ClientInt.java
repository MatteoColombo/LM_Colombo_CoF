package server.control.connection;

import java.io.IOException;
import java.util.List;

import server.control.Controller;
import server.control.instruction.Instruction;
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

	/**
	 * Ask to the player which action he whises to do
	 * @throws IOException
	 */
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

	/**
	 * Sends a general notification to the client
	 * @param dialog
	 * @throws IOException
	 */
	public void notify(Instruction dialog) throws IOException;
	
	/**
	 * Asks to the client which item on sale he wishes to buy
	 * @param itemsOnSale
	 * @throws IOException
	 */
	public void askPlayerItemToBuy(List<OnSaleItem> itemsOnSale) throws IOException;
	
	/**
	 * Asks to the client which item he wishes to sell
	 * @throws IOException
	 */
	public void askWichItemToSell() throws IOException;
	
	/**
	 * Ask to the client to choose the rewards from the specified number of cities
	 * @param citiesNumber
	 * @throws IOException
	 */
	public void askCityToGetNobilityReward(int citiesNumber) throws IOException;
	
	/**
	 * Asks to the client to choose the reward of a permission card
	 * @throws IOException
	 */
	public void askSelectRewardOfPermissionCard() throws IOException;
	
	/**
	 * Asks to the client to choose one free permission card
	 * @throws IOException
	 */
	public void askSelectFreePermissionCard() throws IOException;
	
	public void askConfigurationMethod() throws IOException;
	
}
