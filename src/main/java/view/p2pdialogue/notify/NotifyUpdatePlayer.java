package view.p2pdialogue.notify;

import client.view.ViewInterface;
import model.player.Player;

public class NotifyUpdatePlayer implements Notify {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player player;
	private int index;

	public NotifyUpdatePlayer(Player player, int index) {
		this.player = player;
		this.index = index;
	}

	@Override
	public void execute(ViewInterface view) {
		view.getLocalModel().getPlayers().get(index).setAllButPermissions(player);
	}

}
