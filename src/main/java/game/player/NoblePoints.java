package game.player;

/**
 * A class that represents the NoblePoints owned by a Player.
 * <p>
 * Each Player can have a different {@link #getAmount() amount} of them that can
 * be {@link #increaseAmount(int) increased}.
 * 
 * @author Davide Cavallini
 * @see Player
 */
public class NoblePoints {
	private int amount;

	/**
	 * Initializes the initial amount of NoblePoints owned by this
	 * {@link Player}.
	 * 
	 * @param initialValue
	 *            the initial amount of NoblePoints to be set
	 * @see NoblePoints
	 */
	public NoblePoints(int initialValue) {
		this.amount = initialValue;
	}

	/**
	 * Returns the amount of NoblePoints owned in this moment by this
	 * {@link Player}.
	 * 
	 * @return the actual amount of NoblePoints
	 * @see NoblePoints
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Increases the amount of NoblePoints owned by this {@link Player}.
	 * 
	 * @param value
	 *            the amount used to increment the actual NoblePoints
	 * @see NoblePoints
	 */
	public void increaseAmount(int value) {
		this.amount += value;
	}

}