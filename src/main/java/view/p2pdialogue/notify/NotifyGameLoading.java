package view.p2pdialogue.notify;

import client.view.ViewInterface;

public class NotifyGameLoading implements Notify {

	private static final long serialVersionUID = 7977501288581499309L;

	@Override
	public void execute(ViewInterface view) {
		view.showRoom();
		}
}
