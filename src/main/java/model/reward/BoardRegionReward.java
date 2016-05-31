package model.reward;

import model.board.*;
import model.player.*;

/**
 * A class that represents a specific BoardReward obtainable by a Player after
 * he has achieved some prerequisites.
 * <p>
 * Each BoardRegionReward has a {@link #getBRKey() Region key} and a linked
 * {@link #getBRBonus() Bonus} with its own amount of BVictoryPoints, that can
 * be different depending on the requirements that have to be satisfied to
 * obtain it.
 * 
 * @author Davide Cavallini
 * @see Board
 * @see BoardReward
 * @see BoardColorReward
 * @see Bonus
 * @see BVictoryPoints
 * @see Player
 * @see Region
 * @see Reward
 */
public class BoardRegionReward extends BoardReward {
	private Region bRegionRewardKey;

	/**
	 * Initializes the {@link Region Region key} and the amount of
	 * {@link BVictoryPoints} of this BoardRegionReward.
	 * 
	 * @param rewardKey
	 *            the new key of this BoardRegionReward
	 * @param rewardAmount
	 *            the new amount of BVictoryPoints of this BoardRegionReward
	 * @see BoardRegionReward
	 */
	public BoardRegionReward(Region rewardKey, int rewardAmount) {
		super(rewardAmount);
		this.bRegionRewardKey = rewardKey;
	}

	/**
	 * Returns the {@link Region Region key} of this BoardRegionReward.
	 * 
	 * @return the key of this BoardRegionReward
	 * @see BoardRegionReward
	 */
	public Region getBRKey() {
		return this.bRegionRewardKey;
	}
}