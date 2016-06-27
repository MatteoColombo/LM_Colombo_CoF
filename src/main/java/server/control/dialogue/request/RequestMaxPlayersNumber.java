package server.control.dialogue.request;

import client.control.ViewInterface;

public class RequestMaxPlayersNumber implements Request {

	private static final long serialVersionUID = -1604369008870409827L;

	private int maxPlayersByConfig;

	public RequestMaxPlayersNumber(int maxPlayersByConfig) {
		this.maxPlayersByConfig=maxPlayersByConfig;
	}

	@Override
	public void execute(ViewInterface view) {
		view.printAskPlayersNumber(maxPlayersByConfig);
	}

}
