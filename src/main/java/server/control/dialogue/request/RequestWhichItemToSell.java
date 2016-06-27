package server.control.dialogue.request;

import client.control.ViewInterface;

public class RequestWhichItemToSell implements Request {

	private static final long serialVersionUID = 7274268292494975228L;

	@Override
	public void execute(ViewInterface view) {
		view.printMessage("Write the item that you want to put on sale: (Read the README for the instructions)");
	}

}
