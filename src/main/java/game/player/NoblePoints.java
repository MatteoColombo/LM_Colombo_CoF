package game.player;

/**
 * This is a class that represents the NoblePoints owned by a {@link Player}.
 * <p>
 * Each Player can have a different {@link #getAmount() amount} of them that can
 * be {@link #increaseAmount(int) increased}.
 * 
 * @author Davide_Cavallini
 *
 */
public class NoblePoints {
	private int amount;

	/**
	 * Initializes the initial amount of NoblePoints owned by this
	 * {@link Player}.
	 * 
	 * @param initialValue
	 *            the initial amount to be set
	 */
	public NoblePoints(int initialValue) {
		this.amount = initialValue;
	}

	/**
	 * Returns the amount of NoblePoints owned in that moment by this
	 * {@link Player}.
	 * 
	 * @return the actual amount
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Increases the amount of NoblePoints owned by this {@link Player}.
	 * 
	 * @param value
	 *            the amount used to increment
	 */
	public void increaseAmount(int value) {
		this.amount += value;
	}

}