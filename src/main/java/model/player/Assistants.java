package model.player;

/**
 * A class that represents the Assistants owned by a Player.
 * <p>
 * Each Player can have a different {@link #getAmount() amount} of them that can
 * be {@link #increaseAmount(int) increased} or {@link #decreaseAmount(int)
 * decreased} according to the context.
 * 
 * @author Davide Cavallini
 * @see Player
 */
public class Assistants {
	private int amount;

	/**
	 * Initializes the initial amount of Assistants owned by this {@link Player}.
	 * 
	 * @param initialValue
	 *            the initial amount of Assistants to be set
	 * @see Assistants
	 */
	public Assistants(int initialValue) {
		this.amount = initialValue;
	}

	/**
	 * Returns the amount of Assistants owned in this moment by this
	 * {@link Player}.
	 * 
	 * @return the actual amount of Assistants
	 * @see Assistants
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Increases the amount of Assistants owned by this {@link Player}.
	 * 
	 * @param value
	 *            the amount used to increment the actual Assistants
	 * @see Assistants
	 */
	public void increaseAmount(int value) {
		this.amount += value;
	}

	/**
	 * Decreases the amount of Assistants owned by this {@link Player}.
	 * 
	 * @param value
	 *            the amount used to decrement the actual Assistants
	 * @see Assistants
	 */
	public void decreaseAmount(int value) {
		this.amount -= value;
	}

}