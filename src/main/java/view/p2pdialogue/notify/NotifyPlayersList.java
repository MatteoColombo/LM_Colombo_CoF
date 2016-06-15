package view.p2pdialogue.notify;

import java.util.List;

import client.model.PlayerProperty;
import client.view.ViewInterface;
import javafx.collections.ObservableList;
import model.player.Player;

public class NotifyPlayersList implements Notify{

	private List<Player> players;
	private static final long serialVersionUID = 2398749132373318720L;

	public NotifyPlayersList(List<Player> players) {
		this.players = players;
	}
	@Override
	public void execute(ViewInterface view) {
		ObservableList<PlayerProperty> playersList = view.getLocalModel().getPlayers();
		playersList.clear();
		for(Player player: players) {
			playersList.add(new PlayerProperty().setAllButPermissions(player));
		}
	}

}
