package view.p2pdialogue.notify;

import client.view.ViewInterface;
import model.player.Player;

public class NotifyUpdatePlayer implements Notify{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player player;
	private int index;
	
	public NotifyUpdatePlayer(Player player) {
		this.player = player;
	}
	
	@Override
	public void execute(ViewInterface view) {
		view.getLocalModel().getPlayers().get(index).setAllButPermissions(player);
	}
	
}
