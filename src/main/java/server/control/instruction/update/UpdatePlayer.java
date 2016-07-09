package server.control.instruction.update;

import client.model.ModelInterface;
import server.model.player.Player;

/**
 * Refresh the data of a player
 */
public class UpdatePlayer implements Update {

	private static final long serialVersionUID = 1L;
	private Player player;
	private int index;

	/**
	 * Create a new NotifyUpdatePlayer
	 * @param player the new player data to replace
	 * @param index the index of the player to replace
	 */
	public UpdatePlayer(Player player, int index) {
		this.player = player;
		this.index = index;
	}

	@Override
	public void execute(ModelInterface model) {
		model.updatePlayer(player, index);
	}

}
