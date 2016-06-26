package view.p2pdialogue.notify;

import client.view.ViewInterface;

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
