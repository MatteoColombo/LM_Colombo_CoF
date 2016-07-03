package server.control.dialogue.notify;

import client.control.ViewInterface;
import server.control.dialogue.Dialogue;

/**
 * This is the dialogue which is sent when a simple 	notification to the view is needed
 * @author Matteo Colombo
 *
 */
@FunctionalInterface
public interface Notify extends Dialogue {
	/**
	 * This is the method which, when called by the client-side controller,
	 * updates the UI
	 * 
	 * @param view the client side View
	 */
	public void execute(ViewInterface view);
}
