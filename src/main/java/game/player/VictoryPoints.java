package game.player;

/**
 * A class that represents the VictoryPoints owned by a Player.
 * <p>
 * Each Player can have a different {@link #getAmount() amount} of them that can
 * be {@link #increaseAmount(int) increased}.
 * 
 * @author Davide_Cavallini
 * @see Player
 */
public class VictoryPoints {
	private int amount;

	/**
	 * Initializes the initial amount of VictoryPoints owned by this
	 * {@link Player}.
	 * 
	 * @param initialValue
	 *            the initial amount of VictoryPoints to be set
	 * @see VictoryPoints
	 */
	public VictoryPoints(int initialValue) {
		this.amount = initialValue;
	}

	/**
	 * Returns the amount of VictoryPoints owned in this moment by this
	 * {@link Player}.
	 * 
	 * @return the actual amount of VictoryPoints
	 * @see VictoryPoints
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Increases the amount of VictoryPoints owned by this {@link Player}.
	 * 
	 * @param value
	 *            the amount used to increment the actual VictoryPoints
	 * @see VictoryPoints
	 */
	public void increaseAmount(int value) {
		this.amount += value;
	}
}