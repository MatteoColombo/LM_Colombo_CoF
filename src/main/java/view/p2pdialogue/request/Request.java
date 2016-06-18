package view.p2pdialogue.request;


import client.view.ViewInterface;
import view.p2pdialogue.Dialogue;

public interface Request extends Dialogue {
	/**
	 * This is the method which, when called by the client-side controller,
	 * updates the UI
	 * 
	 * @param view the client side View
	 */
	public void execute(ViewInterface view);
}
