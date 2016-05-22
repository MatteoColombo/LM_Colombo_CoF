package game.player;

/**
 * This is a class that represents the Coins owned by a {@link Player}.
 * <p>
 * Each Player can have a different {@link #getAmount() amount} of them that can
 * be {@link #increaseAmount(int) increased} or {@link #decreaseAmount(int)
 * decreased} according to the context.
 * 
 * @author Davide_Cavallini
 *
 */
public class Coins {
	private int amount;

	/**
	 * Initializes the initial amount of Coins owned by this {@link Player}.
	 * 
	 * @param initialValue
	 *            the initial amount to be set
	 */
	public Coins(int initialValue) {
		this.amount = initialValue;
	}

	/**
	 * Returns the amount of Coins owned in that moment by this {@link Player}.
	 * 
	 * @return the actual amount
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Increases the amount of Coins owned by this {@link Player}.
	 * 
	 * @param value
	 *            the amount used to increment
	 */
	public void increaseAmount(int value) {
		this.amount += value;
	}

	/**
	 * Decreases the amount of Coins owned by this {@link Player}.
	 * 
	 * @param value
	 *            the amount used to decrement
	 */
	public void decreaseAmount(int value) {
		this.amount -= value;
	}

}