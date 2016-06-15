package view.p2pdialogue.notify;

import client.view.ViewInterface;
import model.player.Player;

public class NotifyPlayerUpdate implements Notify {

	private static final long serialVersionUID = -6759831790959231821L;
	private Player player;

	public NotifyPlayerUpdate(Player player) {
		this.player=player;
	}

	@Override
	public void execute(ViewInterface view) {
		// TODO Auto-generated method stub

	}

}
