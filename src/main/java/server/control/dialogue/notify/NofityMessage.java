package server.control.dialogue.notify;

import client.control.ViewInterface;

public class NofityMessage implements Notify{
	private String message;
	public NofityMessage(String message) {
		this.message=message;
	}
	@Override
	public void execute(ViewInterface view) {
		view.printMessage(message);
	}
	
	
}
