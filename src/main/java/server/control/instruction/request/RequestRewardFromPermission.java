package server.control.instruction.request;

import client.control.ViewInterface;
/**
 * Request sent when the player obtain a specia nobility bonus
 * and he can take the reward from a permission card he have;
 */
public class RequestRewardFromPermission implements Request{

	private static final long serialVersionUID = -7193314904800113419L;

	@Override
	public void execute(ViewInterface view) {
		view.changeStatusToNobilityBonus("You can choose the reward from one of you permission cards: (Read the README for instructions)", "fromPermit");
	}

}
