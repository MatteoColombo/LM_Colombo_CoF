package server.model;

/**
 * This is the interace that must be implemented by all the classes interested in some game events
 * @author Matteo Colombo
 *
 */
@FunctionalInterface
public interface GameListener {

	/**
	 * This is the notifier of the game ended status
	 * 
	 */
	public void gameEnded(Game game);
}
