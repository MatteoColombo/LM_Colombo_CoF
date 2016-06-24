package view.p2pdialogue.update;

import java.util.List;

import client.model.ModelInterface;
import model.player.Player;

public class NotifyPlayersList implements Update{

	private List<Player> players;
	private static final long serialVersionUID = 2398749132373318720L;

	public NotifyPlayersList(List<Player> players) {
		this.players = players;
	}
	
	@Override
	public void execute(ModelInterface model) {
		model.setAllPlayers(players);
	}

}
