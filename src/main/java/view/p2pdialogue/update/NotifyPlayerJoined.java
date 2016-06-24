package view.p2pdialogue.update;

import client.model.GameProperty;
import client.model.ModelInterface;
import client.view.ViewInterface;
import model.player.Player;
import view.p2pdialogue.notify.Notify;

public class NotifyPlayerJoined implements Update{

	private static final long serialVersionUID = 2415811892430599147L;
	private Player player;

	public NotifyPlayerJoined(Player player) {
		this.player = player;
	}

	@Override
	public void execute(ModelInterface model) {
		model.playerJoined(player);
	}

}
