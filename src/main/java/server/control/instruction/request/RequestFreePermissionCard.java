package server.control.instruction.request;

import client.control.ViewInterface;

/**
 * Inform the player that he obtained a special nobility bonus, 
 * and he can take a free permission card
 */
public class RequestFreePermissionCard implements Request{

	private static final long serialVersionUID = 936987816256437567L;

	@Override
	public void execute(ViewInterface view) {
		view.changeStatusToNobilityBonus("You can choose a free permission card","takePermission");
	}

}
