package view.p2pdialogue;

import view.client.ViewInterface;

public class DialogueAskWhatActionToDo implements Dialogue {

	private static final long serialVersionUID = 2369205104999605672L;

	@Override
	public void execute(ViewInterface view) {
		view.printAskWhatActionToDo();
	}

}