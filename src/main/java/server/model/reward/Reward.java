package server.model.reward;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import server.model.player.Player;
/**
 * a reward is an aggregation of Bonus
 * @author gianpaolobranca
 *
 */
public class Reward implements Serializable {	

	private static final long serialVersionUID = 278887920051830885L;
	private ArrayList<Bonus> bonusList = new ArrayList<>();
	/**
	 * Generic constructor for the reward
	 * @param bonusList the given list of bonus for initialize the reward
	 */
	public Reward(List<Bonus> bonusList) {
		this.bonusList = (ArrayList<Bonus>) bonusList;
	}
	/**
	 * Constructor for a reward with only one bonus
	 * @param singleBonus the given bonus that will be inserted in the reward
	 */
	public Reward(Bonus singleBonus)  {
		this.bonusList.add(singleBonus);
	}
	/**
	 * Constructor for a randomly generated Reward from a set of Bonus
	 * @param availableBonus The Reward choose a Bonus among this array. 
	 * Usually this should be the allStandardBonus from the Bonus class, but it could be different
	 * @param differentBonus how much different Bonus should contains the Reward at most
	 * @param value a parameter to generate different Rewards.
	 * The higher it is, the bigger the average generated reward will be.
	 */
	public Reward(Bonus[] availableBonus, int maxdifferentBonus, int value) {
		
		if(maxdifferentBonus > availableBonus.length) {
			throw new IllegalArgumentException();
		}
		FlagTable flagTable = new FlagTable(availableBonus.length);
		Random r = new Random();
		int differentBonus = r.nextInt(maxdifferentBonus) +1;
		int bonusToInsert = differentBonus;
		
		while(bonusToInsert > 0) {
			
			int indexBonus = r.nextInt(availableBonus.length);
			while(flagTable.isFlagged(indexBonus)) {
				indexBonus = r.nextInt(availableBonus.length);
			}
			// the following statement generate a balanced amount for the choosen bonus
			int amount = (r.nextInt(value) / (availableBonus[indexBonus].getValue() * differentBonus)) +1;
			Bonus buffer = availableBonus[indexBonus].newCopy(amount);
			bonusList.add(buffer);
			flagTable.flag(indexBonus);
			bonusToInsert--;
		}
	}
	/**
	 * apply all the bonus in the list to the given player
	 * @param p the given player
	 * @see Player
	 */
	public void assignBonusTo(Player p) {
		this.bonusList.forEach(bonus -> bonus.assignBonusTo(p));
	}
	public List<Bonus> getGeneratedRewards(){
		return this.bonusList;
	}
}
