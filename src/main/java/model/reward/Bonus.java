package model.reward;

import model.player.Player;
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
	
	private static final Bonus[] nobilityBonus = {
			new BRewardFromPermissionCard(),
			new BExtraRewardFromCity(1),
			new BTakePermissionCard() };
	
	private int amount;
	/**
	 * Create a new Bonus object.
	 * @param amount any positive integer 
	 */
	public Bonus(int amount) {
		this.amount = amount;
	}
	/**
	 * return the cardinality of this object
	 * @return the amount of the basic bonus object
	 */
	public int getAmount() {
		return this.amount;
	}
	
	/**
	 * 
	 * @return the static array of all standard kind of bonus in the game, available for the permission cards
	 */
	public static Bonus[] getAllStandardBonus() {
		return allStandardBonus.clone();
	}
	/**
	 * 
	 * @return only the bonus available for the cities
	 */
	public static Bonus[] getCityBonus() {
		return new Bonus[] { allStandardBonus[0],
							 allStandardBonus[1],
							 allStandardBonus[2],
							 allStandardBonus[3],
							 allStandardBonus[4]};

	}
	/**
	 * 
	 * @return all the concrete class that implements Bonus existing in the game
	 */
	public static Bonus[] getAllBonus() {
		return new Bonus[] { allStandardBonus[0],
				 			 allStandardBonus[1],
				 			 allStandardBonus[2],
				 			 allStandardBonus[3],
				 			 allStandardBonus[4], 
				 			 allStandardBonus[5],
				 			 nobilityBonus[0],
				 			 nobilityBonus[1],
				 			 nobilityBonus[2]}; 
	}
	/**
	 * the VALUE is a static number in each concrete bonus representing the impact on the game, his power.
	 * <p> it is used in the random constructor of the reward in order to create balanced reward
	 * @see Reward
	 */
	protected abstract int getValue();
	
	/**
	 * factory method to create a new Bonus of the same type of the one calling the method, but with a specified amount
	 * @param amount the cardinality of the Bonus
	 */
	public abstract Bonus newCopy(int amount);
	
	/**
	 * apply the Bonus to the given player, modifying his attributes
	 * @param Player the given player
	 * @see Player
	 */
	public abstract void assignBonusTo(Player p);
	
	public abstract String getTagName();
}
