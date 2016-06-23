package view.p2pdialogue.update;

import client.model.GameProperty;
import client.model.ModelInterface;
import client.view.ViewInterface;
import model.player.Player;

public class NotifyUpdatePlayer implements Update {

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

	/*@Override
	public void execute(ViewInterface view) {
		view.updatePlayer(player, index);
	}*/

	@Override
	public void execute(ModelInterface model) {
		model.updatePlayer(player, index);
	}

}
