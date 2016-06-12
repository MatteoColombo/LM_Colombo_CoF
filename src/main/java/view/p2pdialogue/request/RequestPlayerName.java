package view.p2pdialogue.request;

import client.view.ViewInterface;

public class RequestPlayerName implements  Request{

	private static final long serialVersionUID = 7042996601614394078L;

	@Override
	public void execute(ViewInterface view) {
		view.printAskPlayerName();
	}

}
