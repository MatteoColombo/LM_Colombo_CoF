package server.control.instruction.request;

import client.control.ViewInterface;
/**
 * Request sent during the gaming setup.
 * Ask the player which map he want to use
 */
public class RequestWichMapToUse implements Request {

	private static final long serialVersionUID = 3660956964273763957L;

	@Override
	public void execute(ViewInterface view) {
		view.printAskWhichMapToUse();
	}

}
