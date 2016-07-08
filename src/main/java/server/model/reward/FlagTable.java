package server.model.reward;

import java.util.Random;

/**
 * A class that consists in an array of booleans used for the random creation of
 * the Rewards.
 * 
 * @see Random
 * @see Reward
 */
public class FlagTable {
	private boolean[] flags;

	/**
	 * Initializes the array of booleans with a specific length and sets all of
	 * its elements as <code>false</code>.
	 * 
	 * @param length
	 *            the dimension of the array
	 * @see FlagTable
	 */
	public FlagTable(int length) {
		flags = new boolean[length];
		this.unflagAll();
	}

	/**
	 * Sets all of the elements of the array as <code>false</code>.
	 * 
	 * @see FlagTable
	 */
	public void unflagAll() {
		for (int i = flags.length - 1; i >= 0; i--) {
			flags[i] = false;
		}
	}

	/**
	 * Sets a specific element of the array as <code>true</code>.
	 * 
	 * @param index
	 *            the position of the desired element
	 * @see FlagTable
	 */
	public void flag(int index) {
		flags[index] = true;
	}

	/**
	 * Returns a specific element of the array.
	 * 
	 * @param index
	 *            the position of the desired element
	 * @return <code>true</code> or <code>false</code>
	 * @see FlagTable
	 */
	public boolean isFlagged(int index) {
		return flags[index];
	}
}
