package view.p2pdialogue.notify;

import java.io.Serializable;
import java.util.List;

import client.view.ViewInterface;
import model.player.Player;

public class NotifyPlayersList implements Notify{

	private List<Player> players;
	private static final long serialVersionUID = 2398749132373318720L;

	public NotifyPlayersList(List<Player> players) {
		this.players=players;
	}
	@Override
	public void execute(ViewInterface view) {
		// TODO Auto-generated method stub
		
	}

}
