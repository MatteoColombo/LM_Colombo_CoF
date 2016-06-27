package server.model.reward;

import java.awt.Color;

import server.model.board.Board;
import server.model.player.Player;

/**
 * A class that represents a specific BoardReward obtainable by a Player after
 * he has achieved some prerequisites.
 * <p>
 * Each BoardColorReward has a {@link #getBRKey() Color key} and a linked
 * {@link #getBRBonus() Bonus} with its own amount of BVictoryPoints, that can
 * be different depending on the requirements that have to be satisfied to
 * obtain it.
 * 
 * @author Davide Cavallini
 * @see Board
 * @see BoardReward
 * @see BoardRegionReward
 * @see Bonus
 * @see BVictoryPoints
 * @see Color
 * @see Player
 * @see Reward
 */
public class BoardColorReward extends BoardReward {
	private Color bColorRewardKey;

	/**
	 * Initializes the {@link Color Color key} and the amount of
	 * {@link BVictoryPoints} of this BoardColorReward.
	 * 
	 * @param rewardKey
	 *            the new key of this BoardColorReward
	 * @param rewardAmount
	 *            the new amount of BVictoryPoints of this BoardColorReward
	 * @see BoardColorReward
	 */
	public BoardColorReward(Color rewardKey, int rewardAmount) {
		super(rewardAmount);
		this.bColorRewardKey = rewardKey;
	}

	/**
	 * Returns the {@link Color Color key} of this BoardColorReward.
	 * 
	 * @return the key of this BoardColorReward
	 * @see BoardColorReward
	 */
	public Color getBRKey() {
		return this.bColorRewardKey;
	}
}