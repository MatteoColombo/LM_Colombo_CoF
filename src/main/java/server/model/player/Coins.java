package server.model.player;

import java.io.Serializable;

/**
 * A class that represents the Coins owned by a Player.
 * <p>
 * Each Player can have a different {@link #getAmount() amount} of them that can
 * be {@link #increaseAmount(int) increased} or {@link #decreaseAmount(int)
 * decreased} according to the context.
 * 
 * @author Davide Cavallini
 * @see Player
 */
public class Coins implements Serializable {

	private static final long serialVersionUID = -5634469152427209044L;
	private int amount;

	/**
	 * Initializes the initial amount of Coins owned by this {@link Player}.
	 * 
	 * @param initialValue
	 *            the initial amount of Coins to be set
	 * @see Coins
	 */
	public Coins(int initialValue) {
		this.amount = initialValue;
	}

	/**
	 * Returns the amount of Coins owned in this moment by this {@link Player}.
	 * 
	 * @return the actual amount of Coins
	 * @see Coins
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Increases the amount of Coins owned by this {@link Player}.
	 * 
	 * @param value
	 *            the amount used to increment the actual Coins
	 * @see Coins
	 */
	public void increaseAmount(int value) {
		this.amount += value;
	}

	/**
	 * Decreases the amount of Coins owned by this {@link Player}.
	 * 
	 * @param value
	 *            the amount used to decrement the actual Coins
	 * @see Coins
	 */
	public void decreaseAmount(int value) {
		this.amount -= value;
	}

}