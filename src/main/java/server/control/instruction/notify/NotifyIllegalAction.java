package server.control.instruction.notify;

import client.control.ViewInterface;
import server.model.action.IllegalActionException;
/**
 * Notify that something wrong occurred and send the exception to the client
 *
 */
public class NotifyIllegalAction implements Notify {

	private static final long serialVersionUID = 7092329924416106089L;
	private IllegalActionException e;
	
	/**
	 * Create a new NotifyIllegalAction
	 * @param e the exception raised
	 */
	public NotifyIllegalAction(IllegalActionException e) {
		this.e=e;
	}
	@Override
	public void execute(ViewInterface view) {
		view.printIllegalAction(e);
	}

}
