package server.control.instruction.request;

import client.control.ViewInterface;

public class RequestConfigurationMethod implements Request {

	private static final long serialVersionUID = -8138974998559504067L;

	@Override
	public void execute(ViewInterface view) {
		view.printAskConfigurationMethod();
	}

}
