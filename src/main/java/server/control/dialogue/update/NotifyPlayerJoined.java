package server.control.dialogue.update;

import client.model.ModelInterface;
import server.model.player.Player;

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
