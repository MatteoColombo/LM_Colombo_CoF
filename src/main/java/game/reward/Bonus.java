package game.reward;

import game.player.Player;
/**
 * A Bonus is a certain quantity of given game object
 * @author gianpaolobranca
 *
 */
public abstract class Bonus {
	
	private static final Bonus[] allStandardBonus = { 
			 new BCoins(1),
			 new BAssistants(1),
			 new BNobilityPoints(1),
			 new BVictoryPoints(1),
			 new BPoliticCards(1),
			 new BExtraMainAction()}; // 1 by default
	
	private int amount;
	/**
	 * Create a new Bonus object.
	 * @param amount any positive integer 
	 */
	public Bonus(int amount) {
		this.amount = amount;
	}
	/**
	 * 
	 * @return the amount of the basic bonus object
	 */
	public int getAmount() {
		return this.amount;
	}
	
	/**
	 * 
	 * @return the static array of all standard kind of bonus in the game
	 */
	public static Bonus[] getAllStandardBonus() {
		return allStandardBonus.clone();
	}
	
	public static Bonus[] getCityBonus() {
		return new Bonus[] { allStandardBonus[0],
							 allStandardBonus[1],
							 allStandardBonus[2],
							 allStandardBonus[3],
							 allStandardBonus[4]};

	}
	public abstract int getValue();
	public abstract Bonus newCopy(int amount);
	public abstract void assignBonusTo(Player p);
}
