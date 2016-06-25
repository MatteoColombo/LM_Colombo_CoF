package view.p2pdialogue.request;

import client.view.ViewInterface;

public class RequestFreePermissionCard implements Request{

	private static final long serialVersionUID = 936987816256437567L;

	@Override
	public void execute(ViewInterface view) {
		view.printMessage("You can choose a free permission card: (Read the README for the instructions)");
	}

}
