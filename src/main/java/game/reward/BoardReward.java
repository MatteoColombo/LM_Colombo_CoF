package game.reward;

import java.awt.Color;

import game.board.*;
import game.player.*;

/**
 * A class that represents a BoardReward obtainable by a Player after he has
 * achieved some prerequisites.
 * <p>
 * Each BoardReward has a {@link #getBRKey() name} and a linked
 * {@link #getBRBonus() Bonus} with its own amount of BVictoryPoints, that can
 * be different depending on the requirements that have to be satisfied to
 * obtain it.
 * 
 * @author Davide Cavallini
 * @see Board
 * @see Bonus
 * @see BVictoryPoints
 * @see Player
 * @see Reward
 */
public abstract class BoardReward {
	private BVictoryPoints bRewardBonus;

	/**
	 * Initializes the name and the amount of {@link BVictoryPoints} of this
	 * BoardReward.
	 * <p>
	 * <b>N.B.</b> If the new BoardReward doesn't have a String as name but it
	 * has some other kind of "identification attribute", like a {@link Color}
	 * that has a sequence of numbers, it <b><U>MUST</U></b> be somehow
	 * converted into a String (such as using the toString() method)!
	 * 
	 * @param rewardName
	 *            the new name of this BoardReward
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