package view.p2pdialogue.notify;

import client.view.ViewInterface;

public class NotifyYourTurn implements Notify {

	private static final long serialVersionUID = -6910126532715632578L;

	@Override
	public void execute(ViewInterface view) {
		view.getLocalModel().canNotDoMainAction().set(false);
		view.getLocalModel().canNotDoSideAction().set(false);
	}

}
