package server.control.dialogue.request;


import client.control.ViewInterface;
import server.control.dialogue.Dialogue;

/**
 * This is the dialogue which is sent when a response from the client is requested
 * @author Matteo Colombo
 *
 */
@FunctionalInterface
public interface Request extends Dialogue {
	/**
	 * This is the method which, when called by the client-side controller,
	 * updates the UI
	 * 
	 * @param view the client side View
	 */
	public void execute(ViewInterface view);
}
