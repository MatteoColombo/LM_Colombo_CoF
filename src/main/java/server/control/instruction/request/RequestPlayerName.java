package server.control.instruction.request;

import client.control.ViewInterface;
/**
 * Ask the name to the player
 */
public class RequestPlayerName implements  Request{

	private static final long serialVersionUID = 7042996601614394078L;

	@Override
	public void execute(ViewInterface view) {
		view.printAskPlayerName();
	}

}
