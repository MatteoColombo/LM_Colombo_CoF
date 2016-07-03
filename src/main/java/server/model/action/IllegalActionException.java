package server.model.action;

/**
 * This is the Exception which is thrown when the client tries to perform an
 * illegal action it is thrown both when the message is wrong and when the
 * player doesn't have enough objects to pay the action
 * 
 * @author Matteo Colombo
 *
 */
public class IllegalActionException extends Exception {

	private static final long serialVersionUID = -3268110485368713917L;

	/**
	 * Receives the error messages and stores it
	 * @param s
	 */
	public IllegalActionException(String s) {
		super(s);
	}
}
