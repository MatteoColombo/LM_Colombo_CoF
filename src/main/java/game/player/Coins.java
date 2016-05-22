package game.player;
/**
 * This is a class that represents the Coins owned by a {@link Player}.
 * @author Davide_Cavallini
 *
 */

public class Coins {
	private int amount;
/**
 * 
 * @param value
 */
	public Coins(int value) {
		this.amount = value;
	}
/**
 * 
 * @return
 */
	public int getAmount() {
		return this.amount;
	}
/**
 * 
 * @param value
 */
	public void increaseAmount(int value) {
		this.amount += value;
	}
/**
 * 
 * @param value
 */
	public void decreaseAmount(int value){
		this.amount -= value;
	}

}