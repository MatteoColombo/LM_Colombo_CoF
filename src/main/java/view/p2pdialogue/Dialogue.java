package view.p2pdialogue;

import java.io.Serializable;

import client.view.ViewInterface;

@FunctionalInterface
public interface Dialogue extends Serializable {

	/**
	 * This is the method which, when called by the client-side controller,
	 * updates the UI
	 * 
	 * @param view the client side View
	 */
	public void execute(ViewInterface view);
}
