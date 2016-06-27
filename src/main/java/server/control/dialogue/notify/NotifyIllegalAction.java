package server.control.dialogue.notify;

import client.control.ViewInterface;
import server.model.action.IllegalActionException;

public class NotifyIllegalAction implements Notify {

	private static final long serialVersionUID = 7092329924416106089L;
	private IllegalActionException e;
	public NotifyIllegalAction(IllegalActionException e) {
		this.e=e;
	}
	@Override
	public void execute(ViewInterface view) {
		view.printIllegalAction(e);
	}

}
