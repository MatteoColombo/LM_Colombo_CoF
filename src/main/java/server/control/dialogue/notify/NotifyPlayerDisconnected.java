package server.control.dialogue.notify;

import client.control.ViewInterface;

public class NotifyPlayerDisconnected implements Notify {

	private static final long serialVersionUID = -1344968769308462074L;
	private String message;

	public NotifyPlayerDisconnected(String name) {
		this.message = "Player " + name + " disconnected!";
	}

	@Override
	public void execute(ViewInterface view) {
		view.printMessage(message);
	}

}
