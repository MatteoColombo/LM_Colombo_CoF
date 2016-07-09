package server.control.instruction.notify;

import client.control.ViewInterface;
/**
 * This is a generic Notify for stream a string message
 * @author Matteo Colombo
 *
 */
public class NofityMessage implements Notify{

	private static final long serialVersionUID = -8326772473680905453L;
	private String message;
	
	/**
	 * create a new NotifyMessage
	 * @param message the message to send
	 */
	public NofityMessage(String message) {
		this.message=message;
	}

	@Override
	public void execute(ViewInterface view) {
		view.printMessage(message);
	}
	
	
}
