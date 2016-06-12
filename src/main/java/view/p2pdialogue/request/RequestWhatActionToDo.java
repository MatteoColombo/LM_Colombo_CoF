package view.p2pdialogue.request;

import client.view.ViewInterface;

public class RequestWhatActionToDo implements Request {

	private static final long serialVersionUID = 2369205104999605672L;

	@Override
	public void execute(ViewInterface view) {
		view.printAskWhatActionToDo();
	}

}
