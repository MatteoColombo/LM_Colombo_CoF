package client.control;

import java.util.List;

import server.model.player.Player;

public interface ViewInterface {
	/**
	 * print the exception
	 * 
	 * @param e
	 */
	public void printIllegalAction(Exception e);

	/**
	 * ask the name to the user
	 */
	public void printAskPlayerName();

	/**
	 * show the main game on the view
	 */
	public void showGame();

	/**
	 * show the waiting room on the view
	 */
	public void showRoom();

	/**
	 * print a generic info message
	 * 
	 * @param message
	 */
	public void printMessage(String message);

	/**
	 * Asks to the player which configuration method to use the method can be
	 * either random o manual. In case of random the city connections and the
	 * nobility track will be automatically generated, also the max number of
	 * player will be choosen by the server
	 */
	public void printAskConfigurationMethod();

	/**
	 * ask how many players should the game have at most. Note that the game
	 * remain in the waiting status for a limited time, after that it starts
	 * regardless how many players are chosen
	 * 
	 * @param max
	 *            the cap of max number of players
	 */
	public void printAskPlayersNumber(int max);

	/**
	 * ask which map the player want to use
	 */
	public void printAskWhichMapToUse();

	/**
	 * ask what action the player want to do. This should be called when the
	 * turn start and after every action completed by the player
	 */
	public void printAskWhatActionToDo();

	/**
	 * notify the player that a special nobility bonus is encountered and an
	 * action is required
	 * 
	 * @param message
	 *            the message related to the bonus
	 * @param status
	 *            the new status for the game
	 */
	public void changeStatusToNobilityBonus(String message, String status);

	/**
	 * starts the market round
	 */
	public void showMarket();

	/**
	 * Publishes the classification when the game ends
	 * 
	 * @param players
	 */
	public void showClassification(List<Player> players);
}
