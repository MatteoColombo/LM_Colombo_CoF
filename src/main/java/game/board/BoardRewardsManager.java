package game.board;

import java.util.List;

import game.reward.BVictoryPoints;
import game.reward.BoardReward;
import game.action.*;
import game.player.*;
import game.reward.*;
import game.board.city.*;
import java.awt.Color;

/**
 * A class that manages the BoardRewards.
 * <p>
 * Whenever a particular condition is satisfied during a Player Action (if he
 * has an Emporium in each City with the same Color or in each City of the same
 * Region), he can be rewarded with a {@link #getBoardReward(String)
 * BoardReward}, but only if {@link #getRemainingBoardRewards() anybody has
 * already received that specific Reward} before him.
 * <p>
 * He will be also rewarded, if {@link #getRemainingBoardKingRewards() it's not
 * empty}, with the greatest of the remaining elements of the BoardKingRewards,
 * that is a list of decreasing sorted BVictoryPoits.
 * 
 * @author Davide Cavallini
 * @see Action
 * @see Board
 * @see BoardReward
 * @see Bonus
 * @see BVictoryPoints
 * @see City
 * @see Color
 * @see Emporium
 * @see King
 * @see Player
 * @see Region
 * @see Reward
 */
public class BoardRewardsManager {
	private List<BoardReward> bRewards;
	private List<BVictoryPoints> bKingRewards;

	/**
	 * Initializes the two lists of all the available {@link BoardReward
	 * BoardRewards} and {@link BVictoryPoints BoardKingRewards}.
	 * <p>
	 * <b>N.B.</b> The BoardKingRewards list <b><U>MUST</U></b> be already
	 * decreasing sorted when received!
	 * 
	 * @param bRewards
	 *            the initial list of BoardRewards
	 * @param bKingRewards
	 *            the initial list of BoardKingRewards
	 * @see BoardRewardsManager
	 */
	public BoardRewardsManager(List<BoardReward> bRewards, List<BVictoryPoints> bKingRewards) {
		this.bRewards = bRewards;
		this.bKingRewards = bKingRewards;
	}

	/**
	 * Returns the remaining elements of this {@link BoardReward BoardRewards}
	 * list.
	 * 
	 * @return the list of available BoardRewards
	 * @see BoardRewardsManager
	 */
	public List<BoardReward> getRemainingBoardRewards() {
		return this.bRewards;
	}

	/**
	 * Returns the remaining elements of this {@link BVictoryPoints
	 * BoardKingRewards} list.
	 * 
	 * @return the list of available BoardKingRewards
	 * @see BoardRewardsManager
	 */
	public List<BVictoryPoints> getRemainingBoardKingRewards() {
		return this.bKingRewards;
	}

	/**
	 * Returns the total amount of BVictoryPoints from both this
	 * {@link BoardReward}, that has been achieved by the {@link Player}, and
	 * the {@link BVictoryPoints BoardKingReward}; it also removes this
	 * BoardReward from its list.
	 * <p>
	 * If the required BoardReward is no longer in the list, it only returns
	 * zero BVictoryPoints.
	 * 
	 * @param bRewardAwarded
	 *            the BoardReward that has been achieved by the Player
	 * @return the total amount of BVictoryPoints to be awarded to the Player;
	 *         zero BVictoryPoints if the required BoardReward is no longer
	 *         available in the list
	 * @see BoardRewardsManager
	 */
	public BVictoryPoints getBoardReward(String bRewardAwarded) {
		for (BoardReward br : this.bRewards) {
			if (br != null && br.getBRName().equals(bRewardAwarded)) {
				int i = this.bRewards.indexOf(br);
				BVictoryPoints bReward = this.bRewards.remove(i).getBRBonus();
				BVictoryPoints bKingReward = getBoardKingReward();
				return new BVictoryPoints(bReward.getAmount() + bKingReward.getAmount());
			}
		}
		return new BVictoryPoints(0);
	}

	/**
	 * Returns the greatest {@link BVictoryPoints BoardKingReward} left in its
	 * list and removes it from that.
	 * <p>
	 * If the BoardKingRewards list is empty, it only returns zero
	 * BVictoryPoints.
	 * <p>
	 * <b>N.B.</b> <U>This method should not have to be used</U>.
	 * 
	 * @return the greatest BoardKingReward left in its list; zero
	 *         BVictoryPoints if the list is empty
	 * @see BoardRewardsManager
	 */
	public BVictoryPoints getBoardKingReward() {
		if (this.bKingRewards.isEmpty())
			return new BVictoryPoints(0);
		else
			return this.bKingRewards.remove(0);
	}

}