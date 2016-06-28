package server.control.dialogue.notify;

import client.control.ViewInterface;

public class NofityMessage implements Notify{

	private static final long serialVersionUID = -8326772473680905453L;
	private String message;
	public NofityMessage(String message) {
		this.message=message;
	}
	@Override
	public void execute(ViewInterface view) {
		view.printMessage(message);
	}
	
	
}
