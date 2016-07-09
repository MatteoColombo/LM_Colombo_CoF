package server.model.action;

import server.model.player.Player;

/**
 * An Exception that is thrown when the Client tries to perform an illegal
 * Action, both when the message is wrong or the Player doesn't have enough
 * objects to satisfy the Action conditions.
 * 
 * @author Matteo Colombo
 * @see Action
 * @see Exception
 * @see Player
 */
public class IllegalActionException extends Exception {
	private static final long serialVersionUID = -3268110485368713917L;

	/**
	 * Receives the error message and stores it.
	 * 
	 * @param s
	 *            the error message
	 */
	public IllegalActionException(String s) {
		super(s);
	}
}
