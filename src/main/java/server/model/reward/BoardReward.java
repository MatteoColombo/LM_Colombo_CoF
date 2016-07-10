package server.model.reward;

import java.io.Serializable;

import server.model.board.Board;
import server.model.player.Player;

/**
 * A class that represents a generic BoardReward obtainable by a Player after he
 * has achieved some prerequisites.
 * <p>
 * Each generic BoardReward has a {@link #getBRBonus() Bonus} with its own
 * amount of BVictoryPoints, that can be different depending on the requirements
 * that have to be satisfied to obtain it.
 * 
 * @author Davide Cavallini
 * @see Board
 * @see BoardColorReward
 * @see BoardRegionReward
 * @see Bonus
 * @see BVictoryPoints
 * @see Player
 * @see Reward
 */
public abstract class BoardReward implements Serializable {
	private static final long serialVersionUID = -4608779705698062118L;
	private BVictoryPoints bRewardBonus;

	/**
	 * Initializes the amount of {@link BVictoryPoints} of this BoardReward.
	 * 
	 * @param rewardAmount
	 *            the new amount of BVictoryPoints of this BoardReward
	 * @see BoardReward
	 */
	public BoardReward(int rewardAmount) {
		this.bRewardBonus = new BVictoryPoints(rewardAmount);
	}

	/**
	 * Returns the {@link BVictoryPoints} of this BoardReward.
	 * 
	 * @return the BVictoryPoints of this BoardReward
	 * @see BoardReward
	 */
	public BVictoryPoints getBRBonus() {
		return this.bRewardBonus;
	}
}