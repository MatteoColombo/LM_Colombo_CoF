package game.player;
/**
 * 
 * @author Davide_Cavallini
 *
 */
public class NoblePoints {
	private int amount;
/**
 * 
 * @param initialValue
 */
	public NoblePoints(int initialValue) {
		this.amount = initialValue;
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
	
}