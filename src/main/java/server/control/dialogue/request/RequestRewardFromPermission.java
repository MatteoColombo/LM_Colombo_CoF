package server.control.dialogue.request;

import client.control.ViewInterface;

public class RequestRewardFromPermission implements Request{

	private static final long serialVersionUID = -7193314904800113419L;

	@Override
	public void execute(ViewInterface view) {
		view.changeStatusToNobilityBonus("You can choose the reward from one of you permission cards: (Read the README for instructions)", "fromPermit");
	}

}
