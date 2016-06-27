package server.control.dialogue.request;

import client.control.ViewInterface;

public class RequestFreePermissionCard implements Request{

	private static final long serialVersionUID = 936987816256437567L;

	@Override
	public void execute(ViewInterface view) {
		view.changeStatusToNobilityBonus("You can choose a free permission card: (Read the README for the instructions)","takePermission");
	}

}
