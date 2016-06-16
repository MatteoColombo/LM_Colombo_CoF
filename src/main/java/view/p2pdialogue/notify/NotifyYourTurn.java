package view.p2pdialogue.notify;

import client.view.ViewInterface;

public class NotifyYourTurn implements Notify {

	private static final long serialVersionUID = -6910126532715632578L;

	@Override
	public void execute(ViewInterface view) {
		view.getLocalModel().getMyPlayerData().canNotDoMainAction().set(false);
		view.getLocalModel().getMyPlayerData().canNotDoSideAction().set(false);
	}

}
