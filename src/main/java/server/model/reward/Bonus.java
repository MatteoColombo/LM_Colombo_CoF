package server.model.reward;

import java.io.Serializable;

import server.model.board.city.City;
import server.model.board.nobility.NobilityTrack;
import server.model.player.PermissionCard;
import server.model.player.Player;

/**
 * A class that represent a generic Bonus that can be assigned to a Player.
 * <p>
 * Each of them is a different object with an {@link #getAmount() associated
 * amount} and a {@link #getTagName() name tag} that can be {@link #newCopy(int)
 * cloned} and directly {@link #assignBonusTo(Player) assigned to a Player}.
 * <p>
 * Some of all the available/existing ones can be found associated with each
 * {@link #getCityBonus() Cities} and {@link #getAllStandardBonus()
 * PermissionCards}, and the {@link #getNobilityBonus() NobilityTrack}.
 * 
 * @author gianpaolobranca
 * @see City
 * @see NobilityTrack
 * @see PermissionCard
 * @see Player
 */
public abstract class Bonus implements Serializable {

	private static final long serialVersionUID = 8075384354316907271L;

	private static final Bonus[] allStandardBonus = { new BCoins(1), new BAssistants(1), new BNobility(1),
			new BVictoryPoints(1), new BPoliticCards(1), new BExtraMainAction() }; // 1
																					// by
																					// default

	private static final Bonus[] nobilityBonus = { new BRewardFromPermissionCard(), new BExtraRewardFromCity(1),
			new BTakePermissionCard() };

	private int amount;

	/**
	 * Sets the amount of a new Bonus object.
	 * 
	 * @param amount
	 *            the amount of the new Bonus object
	 * @see Bonus
	 */
	public Bonus(int amount) {
		this.amount = amount;
	}

	/**
	 * Returns the amount of the Bonus object.
	 * 
	 * @return the amount of the Bonus object
	 * @see Bonus
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Returns the static array of all the Bonus objects available for a
	 * {@link PermissionCard}.
	 * 
	 * @return all the Bonus object available for a PermissionCard
	 * @see Bonus
	 */
	public static Bonus[] getAllStandardBonus() {
		return allStandardBonus.clone();
	}

	/**
	 * Returns the static array of all the Bonus objects available for a
	 * {@link City}.
	 * 
	 * @return all the Bonus object available for a City
	 * @see Bonus
	 */
	public static Bonus[] getCityBonus() {
		return new Bonus[] { allStandardBonus[0], allStandardBonus[1], allStandardBonus[2], allStandardBonus[3],
				allStandardBonus[4] };

	}

	/**
	 * Returns the static array of all the Bonus objects available for the
	 * {@link NobilityTrack}.
	 * 
	 * @return all the Bonus object available for the NobilityTrack
	 * @see Bonus
	 */
	public static Bonus[] getNobilityBonus() {
		return new Bonus[] { allStandardBonus[0], allStandardBonus[1],
				// [2] is nobility
				allStandardBonus[3], allStandardBonus[4], allStandardBonus[5], nobilityBonus[0], nobilityBonus[1],
				nobilityBonus[2] };
	}

	/**
	 * Returns the <U>VALUE</U> that is a static number in each concrete bonus
	 * representing the impact on the game, his power.
	 * <p>
	 * It's used in the random constructor of the {@link Reward} in order to
	 * generate balanced ones.
	 * 
	 * @see Bonus
	 */
	protected abstract int getValue();

	/**
	 * Creates a new Bonus of the same type of the one calling the method, but
	 * with a new specified amount.
	 * 
	 * @param amount
	 *            the new amount of the copy of the calling Bonus
	 * @see Bonus
	 */
	public abstract Bonus newCopy(int amount);

	/**
	 * Awards the Bonus to this {@link Player}.
	 * 
	 * @param p
	 *            the Player that will be rewarded
	 * @see Bonus
	 */
	public abstract void assignBonusTo(Player p);

	/**
	 * Returns the tag name of this Bonus.
	 * 
	 * @return the tag name of this Bonus
	 * @see Bonus
	 */
	public abstract String getTagName();
}
