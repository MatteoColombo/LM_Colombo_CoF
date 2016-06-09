package view.p2pdialogue;

import view.client.ViewInterface;

public class DialogueAskMaxPlayersNumber implements Dialogue {

	private static final long serialVersionUID = -1604369008870409827L;

	private int maxPlayersByConfig;

	public DialogueAskMaxPlayersNumber(int maxPlayersByConfig) {
		this.maxPlayersByConfig=maxPlayersByConfig;
	}

	@Override
	public void execute(ViewInterface view) {
		view.printAskPlayersNumber(maxPlayersByConfig);
	}

}
