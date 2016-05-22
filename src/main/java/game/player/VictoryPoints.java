package game.player;

/**
 * This is a class that represents the VictoryPoints owned by a {@link Player}.
 * <p>
 * Each Player can have a different {@link #getAmount() amount} of them that can
 * be {@link #increaseAmount(int) increased}.
 * 
 * @author Davide_Cavallini
 *
 */
public class VictoryPoints {
	private int amount;

	/**
	 * Initializes the initial amount of VictoryPoints owned by this
	 * {@link Player}.
	 * 
	 * @param initialValue
	 *            the initial amount to be set
	 */
	public VictoryPoints(int initialValue) {
		this.amount = initialValue;
	}

	/**
	 * Returns the amount of VictoryPoints owned in that moment by this
	 * {@link Player}.
	 * 
	 * @return the actual amount
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Increases the amount of VictoryPoints owned by this {@link Player}.
	 * 
	 * @param value
	 *            the amount used to increment
	 */
	public void increaseAmount(int value) {
		this.amount += value;
	}
}