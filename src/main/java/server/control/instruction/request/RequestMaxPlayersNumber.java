package server.control.instruction.request;

import client.control.ViewInterface;

/**
 * Request sent during the gaming setup.
 * It ask the player how many players he want to play with
 */
public class RequestMaxPlayersNumber implements Request {

	private static final long serialVersionUID = -1604369008870409827L;

	private int maxPlayersByConfig;

	/**
	 * Create a new RequestMaxPlayersNumber
	 * @param maxPlayersByConfig the maximum player the client can play with
	 */
	public RequestMaxPlayersNumber(int maxPlayersByConfig) {
		this.maxPlayersByConfig=maxPlayersByConfig;
	}

	@Override
	public void execute(ViewInterface view) {
		view.printAskPlayersNumber(maxPlayersByConfig);
	}

}
