package server.control.instruction.request;

import client.control.ViewInterface;
/**
 * Request for the market round
 * it ask the player which item he want to sell
 */
public class RequestWhichItemToSell implements Request {

	private static final long serialVersionUID = 7274268292494975228L;

	@Override
	public void execute(ViewInterface view) {
		view.printMessage("Write the item that you want to put on sale: (Read the README for the instructions)");
	}

}
