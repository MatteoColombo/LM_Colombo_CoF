package view.p2pdialogue.request;

import client.view.ViewInterface;

public class RequestWichMapToUse implements Request {

	private static final long serialVersionUID = 3660956964273763957L;

	@Override
	public void execute(ViewInterface view) {
		view.printAskWhichMapToUse();
	}

}
