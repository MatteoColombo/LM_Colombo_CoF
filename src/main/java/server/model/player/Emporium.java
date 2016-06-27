package server.model.player;

/**
 * A class that represents every Emporium in the game.
 * <p>
 * Each of them is assigned to one {@link #getPlayer() Player}.
 * 
 * @author Davide Cavallini
 * @see Player
 */
public class Emporium {
	private Player player;

	/**
	 * Initializes the Emporium with the {@link Player} who own it.
	 * 
	 * @param player
	 *            the new owner of this Emporium
	 * @see Emporium
	 */
	public Emporium(Player player) {
		this.player = player;
	}

	/**
	 * Returns the {@link Player} assigned to this Emporium.
	 * 
	 * @return the owner of this Emporium
	 * @see Emporium
	 */
	public Player getPlayer() {
		return this.player;
	}

}