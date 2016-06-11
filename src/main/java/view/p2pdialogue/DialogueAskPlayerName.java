package view.p2pdialogue;

import client.view.ViewInterface;

public class DialogueAskPlayerName implements Dialogue {

	private static final long serialVersionUID = 7042996601614394078L;

	@Override
	public void execute(ViewInterface view) {
		view.printAskPlayerName();
	}

}
