package server.control.instruction.update;

import java.util.List;

import client.model.ModelInterface;
import server.model.player.Player;

/**
 * This is the update which send the players list, it is sent to the client when it joins a game
 * @author Matteo Colombo
 *
 */
public class NotifyPlayersList implements Update{

	private List<Player> players;
	private static final long serialVersionUID = 2398749132373318720L;

	/**
	 * Sets the players list
	 * @param players
	 */
	public NotifyPlayersList(List<Player> players) {
		this.players = players;
	}
	
	@Override
	public void execute(ModelInterface model) {
		model.setAllPlayers(players);
	}

}
