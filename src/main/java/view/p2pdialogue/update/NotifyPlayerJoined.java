package view.p2pdialogue.update;

import client.model.GameProperty;
import client.view.ViewInterface;
import model.player.Player;
import view.p2pdialogue.notify.Notify;

public class NotifyPlayerJoined implements Update, Notify {

	private static final long serialVersionUID = 2415811892430599147L;
	private Player player;

	public NotifyPlayerJoined(Player player) {
		this.player = player;
	}

	@Override
	public void execute(ViewInterface view) {
		view.playerJoined(player);
	}

	@Override
	public void execute(GameProperty model) {
		model.playerJoined(player);
	}

}
