package server.control.dialogue.request;

import client.control.ViewInterface;

public class RequestCity implements Request {

	private static final long serialVersionUID = -9037886782345936796L;

	@Override
	public void execute(ViewInterface view) {
		view.printMessage("Type in the name of the city");
	}

}
