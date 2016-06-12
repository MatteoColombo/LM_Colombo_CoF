package view.p2pdialogue.notify;

import client.view.ViewInterface;

public class NotifyIllegalAction implements Notify {

	private static final long serialVersionUID = 7092329924416106089L;

	@Override
	public void execute(ViewInterface view) {
		view.printIllegalAction();
	}

}
