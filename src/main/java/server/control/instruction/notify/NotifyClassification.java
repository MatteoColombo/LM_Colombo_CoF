package server.control.instruction.notify;

import java.util.List;
import client.control.ViewInterface;
import server.model.player.Player;

/**
 * This class is used to notify all the players about the classification when the game ends
 * @author Matteo Colombo
 *
 */
public class NotifyClassification implements Notify{

	private static final long serialVersionUID = 2973806154247552960L;
	private List<Player> players;
	/**
	 * Saves the sorted players list
	 * @param players
	 */
	public NotifyClassification(List<Player> players){
		this.players=players;
	}
	@Override
	public void execute(ViewInterface view) {
		view.showClassification(players);
	}

}
