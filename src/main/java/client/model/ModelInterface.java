package client.model;

import java.util.List;

import server.model.board.city.CityConnection;
import server.model.board.council.Council;
import server.model.configuration.Configuration;
import server.model.market.OnSaleItem;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.reward.BVictoryPoints;
import server.model.reward.BoardColorReward;
import server.model.reward.BoardRegionReward;
import server.model.reward.Reward;

/**
 * This is the MVC Model that need to be implemented by all the model implementation on the clients
 * of the client
 *
 */
public interface ModelInterface {
	/**
	 * Initializes the map from an XML file
	 * @param choosen the number of the xml file
	 */
	public void initMap(int choosen);

	/**
	 * getter for the player corresponding to the playing user
	 * 
	 * @return the index fo the player
	 */
	public int getMyIndex();

	/**
	 * notify that your turn is started
	 */
	public void isYourTurn();

	/**
	 * notify that your turn is over
	 */
	public void yourTurnEnded();

	/**
	 * replace the council in the model with the given one
	 * 
	 * @param council
	 *            the given council
	 * @param index
	 *            the index of the council to replace. By convention, the king's
	 *            council has index of -1
	 */
	public void setCouncil(Council council, int index);

	/**
	 * set all the generated bonuses. This should be done only once when the
	 * game is configurated
	 * 
	 * @param bonus
	 *            the list of the bonuses to add to the cities. The list is in
	 *            the same order as the cities in the configuration file
	 */
	public void setBonus(List<Reward> bonus);

	/**
	 * set all the players in the local model. This should be done when a new
	 * player join the game, to give him all the data of the other players
	 * 
	 * @param players
	 *            the players list, included itself as the last one.
	 */
	public void setAllPlayers(List<Player> players);

	/**
	 * replace all the data of the player
	 * 
	 * @param p
	 *            the new data
	 * @param index
	 *            the index of the player to modify
	 */
	public void updatePlayer(Player p, int index);

	/**
	 * add a player to the model when it join the game
	 * 
	 * @param p
	 *            the
	 */
	public void playerJoined(Player p);

	/**
	 * set the configuration of the game
	 * 
	 * @param config
	 */
	public void setConfiguration(Configuration config);

	/**
	 * 
	 * @return the configuration of the game
	 */
	public Configuration getConfiguration();

	/**
	 * replace a face-up permission card on the board when changes in the game
	 * 
	 * @param pc
	 *            the new card
	 * @param region
	 *            the region where it belongs
	 * @param slot
	 *            the slot where it belong
	 */
	public void setPermission(PermissionCard pc, int region, int slot);

	/**
	 * Method used to add an emporium of a player in a city
	 * @param city the city in which the emporium was built
	 * @param playerIndex the index of the player
	 */
	public void buildEmporium(String city, int playerIndex);

	/**
	 * changes the king's position on the map
	 * 
	 * @param location
	 *            the name of the cities where the king has moved
	 */
	public void setKingLocation(String location);

	/**
	 * modify all the board rewards when a changes occure
	 * 
	 * @param kingReward
	 *            the list of king's rewards
	 * @param colorReward
	 *            the list of color's rewards
	 * @param regionReward
	 *            the list of region's rewards
	 */
	public void updateBoardReward(List<BVictoryPoints> kingReward, List<BoardColorReward> colorReward,
			List<BoardRegionReward> regionReward);

	/**
	 * Saves the market items in the model so that they can be manipulated and shown to the client
	 * @param items
	 */
	public void setMarket(List<OnSaleItem> items);

	/**
	 * Sets the city connections after that the map with the cities was loaded
	 * 
	 * @param connections
	 */
	public void setConnections(List<CityConnection> connections);

	/**
	 * Notifies that the market session ended
	 */
	public void endMarket();
}
