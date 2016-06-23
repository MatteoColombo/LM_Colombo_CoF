package view.p2pdialogue.update;

import java.util.List;

import client.model.GameProperty;
import client.model.ModelInterface;
import client.model.PlayerProperty;
import client.view.ViewInterface;
import model.player.Player;
import view.p2pdialogue.notify.Notify;

public class NotifyPlayersList implements Update, Notify{

	private List<Player> players;
	private static final long serialVersionUID = 2398749132373318720L;

	public NotifyPlayersList(List<Player> players) {
		this.players = players;
	}
	
	//this is used only for the CLI
	@Override
	public void execute(ViewInterface view) {
		view.setAllPlayers(players);
	}
	
	@Override
	public void execute(ModelInterface model) {
		model.setAllPlayers(players);
	}

}
