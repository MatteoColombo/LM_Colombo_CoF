package server.control.instruction.request;

import client.control.ViewInterface;
/**
 * Request the user with configuration he want tu use;
 * the anser can be "manual" or "random"
 */
public class RequestConfigurationMethod implements Request {

	private static final long serialVersionUID = -8138974998559504067L;

	@Override
	public void execute(ViewInterface view) {
		view.printAskConfigurationMethod();
	}

}
