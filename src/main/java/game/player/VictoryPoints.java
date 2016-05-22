package game.player;
/**
 * 
 * @author Davide_Cavallini
 *
 */
public class VictoryPoints {
	private int amount;
/**
 * 
 * @param initialValue
 */
	public VictoryPoints(int initialValue) {
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