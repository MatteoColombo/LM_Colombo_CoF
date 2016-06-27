package server.control.dialogue.notify;

import client.control.ViewInterface;
import server.control.dialogue.Dialogue;


public interface Notify extends Dialogue {
	/**
	 * This is the method which, when called by the client-side controller,
	 * updates the UI
	 * 
	 * @param view the client side View
	 */
	public void execute(ViewInterface view);
}
