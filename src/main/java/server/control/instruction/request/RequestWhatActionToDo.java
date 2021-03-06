package server.control.instruction.request;

import client.control.ViewInterface;
/**
 * Request send when the player can do an action
 */
public class RequestWhatActionToDo implements Request {

	private static final long serialVersionUID = 2369205104999605672L;

	@Override
	public void execute(ViewInterface view) {
		view.printAskWhatActionToDo();
	}

}
