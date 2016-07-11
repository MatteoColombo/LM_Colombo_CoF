package server.model;

/**
 * An interface that have to be implemented by all the classes interested in
 * some Game events.
 * 
 * @author Matteo Colombo
 * @see Game
 */
@FunctionalInterface
public interface GameListener {

	/**
	 * Notifiers the {@link Game} ended status.
	 * 
	 * @see GameListener
	 */
	public void gameEnded(Game game);
}
