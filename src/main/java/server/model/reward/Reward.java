package server.model.reward;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import server.model.player.Player;

/**
 * A class that represents a generic Reward as an aggregation of Bonus.
 * <p>
 * {@link #getGeneratedRewards() Each of them} can be randomly generated or
 * created adding single Bonuses; they are {@link #assignBonusTo(Player)
 * rewarded to a Player} that has achieved their conditions.
 * 
 * @author gianpaolobranca
 * @see Bonus
 * @see Player
 * @see Random
 */
public class Reward implements Serializable {
	private static final long serialVersionUID = 278887920051830885L;
	private ArrayList<Bonus> bonusList = new ArrayList<>();

	/**
	 * Initializes the Reward with a selected list of {@link Bonus Bonuses}.
	 * 
	 * @param bonusList
	 *            the given list of Bonuses that will be inserted in the Reward
	 * @see Reward
	 */
	public Reward(List<Bonus> bonusList) {
		this.bonusList = (ArrayList<Bonus>) bonusList;
	}

	/**
	 * Initializes the Reward with a single {@link Bonus}.
	 * 
	 * @param singleBonus
	 *            the given Bonus that will be inserted in the Reward
	 * @see Reward
	 */
	public Reward(Bonus singleBonus) {
		this.bonusList.add(singleBonus);
	}

	/**
	 * Initializes the Reward with a random selection from a given set of
	 * {@link Bonus Bonuses}.
	 * 
	 * @param availableBonus
	 *            the available Bonuses that will be randomly picked fore the
	 *            Reward
	 * @param differentBonus
	 *            the number of Bonuses that the Reward should contains at most
	 * @param value
	 *            a parameter used to generate Rewards; the higher it is, the
	 *            bigger the average generated Reward will be
	 * @see Reward
	 */
	public Reward(Bonus[] availableBonus, int maxdifferentBonus, int value) {

		if (maxdifferentBonus > availableBonus.length) {
			throw new IllegalArgumentException();
		}
		FlagTable flagTable = new FlagTable(availableBonus.length);
		Random r = new Random();
		int differentBonus = r.nextInt(maxdifferentBonus) + 1;
		int bonusToInsert = differentBonus;

		while (bonusToInsert > 0) {

			int indexBonus = r.nextInt(availableBonus.length);
			while (flagTable.isFlagged(indexBonus)) {
				indexBonus = r.nextInt(availableBonus.length);
			}
			// the following statement generate a balanced amount for the
			// chosen bonus
			int amount = (r.nextInt(value) / (availableBonus[indexBonus].getValue() * differentBonus)) + 1;
			Bonus buffer = availableBonus[indexBonus].newCopy(amount);
			bonusList.add(buffer);
			flagTable.flag(indexBonus);
			bonusToInsert--;
		}
	}

	/**
	 * Awards all the {@link Bonus Bonuses} in the Reward list to this
	 * {@link Player}.
	 * 
	 * @param p
	 *            the Player that will be rewarded
	 * @see Reward
	 */
	public void assignBonusTo(Player p) {
		this.bonusList.forEach(bonus -> bonus.assignBonusTo(p));
	}

	/**
	 * Returns the list of the all the {@link Bonus Bonuses} contained in the
	 * Reward.
	 * 
	 * @return the list of the all the Bonuses of the Reward
	 * @see Reward
	 */
	public List<Bonus> getGeneratedRewards() {
		return this.bonusList;
	}
}
