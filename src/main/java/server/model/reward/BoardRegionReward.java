package server.model.reward;

import server.model.board.Board;
import server.model.board.Region;
import server.model.player.Player;

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
	private static final long serialVersionUID = -159897097059005958L;
	private transient Region bRegionRewardKey;

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

	/**
	 * Returns a new copy of this BoardRegionReward.
	 * 
	 * @return a new copy of this BoardRegionReward
	 * @see BoardRegionReward
	 */
	public BoardRegionReward newCopy() {
		return new BoardRegionReward(bRegionRewardKey, this.getBRBonus().getAmount());
	}
}