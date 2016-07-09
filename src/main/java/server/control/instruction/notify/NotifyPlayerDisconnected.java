package server.control.instruction.notify;

import client.control.ViewInterface;
/**
 * Notify that a player disconnected
 */
public class NotifyPlayerDisconnected implements Notify {

	private static final long serialVersionUID = -1344968769308462074L;
	private String message;

	/**
	 * Create a new NotifyPlayerDisconnected
	 * @param name the name of the disconnected player
	 */
	public NotifyPlayerDisconnected(String name) {
		this.message = "Player " + name + " disconnected!";
	}

	@Override
	public void execute(ViewInterface view) {
		view.printMessage(message);
	}

}
