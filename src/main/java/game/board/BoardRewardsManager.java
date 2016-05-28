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
 * @see BoardColorReward
 * @see BoardRegionReward
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
	private List<BoardColorReward> bColorRewards;
	private List<BoardRegionReward> bRegionRewards;
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
	public BoardRewardsManager(List<BoardColorReward> bColorRewards, List<BoardRegionReward> bRegionRewards,
			List<BVictoryPoints> bKingRewards) {
		this.bColorRewards = bColorRewards;
		this.bRegionRewards = bRegionRewards;
		this.bKingRewards = bKingRewards;
	}

	/**
	 * Returns the remaining elements of this {@link BoardReward BoardRewards}
	 * list.
	 * 
	 * @return the list of available BoardRewards
	 * @see BoardRewardsManager
	 */
	public List<BoardColorReward> getRemainingBColorRewards() {
		return this.bColorRewards;
	}

	public List<BoardRegionReward> getRemainingBRegionRewards() {
		return this.bRegionRewards;
	}

	/**
	 * Returns the remaining elements of this {@link BVictoryPoints
	 * BoardKingRewards} list.
	 * 
	 * @return the list of available BoardKingRewards
	 * @see BoardRewardsManager
	 */
	public List<BVictoryPoints> getRemainingBKingRewards() {
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
	public BVictoryPoints getBoardReward(Color bColorRewardAwarded) {
		for (BoardColorReward br : this.bColorRewards) {
			if (br != null && br.getBRKey().equals(bColorRewardAwarded)) {
				int i = this.bColorRewards.indexOf(br);
				BVictoryPoints bColorReward = this.bColorRewards.remove(i).getBRBonus();
				BVictoryPoints bKingReward = getBoardKingReward();
				return new BVictoryPoints(bColorReward.getAmount() + bKingReward.getAmount());
			}
		}
		return new BVictoryPoints(0);
	}

	public BVictoryPoints getBoardReward(Region bRegionRewardAwarded) {
		for (BoardRegionReward br : this.bRegionRewards) {
			if (br != null && br.getBRKey().equals(bRegionRewardAwarded)) {
				int i = this.bRegionRewards.indexOf(br);
				BVictoryPoints bRegionReward = this.bRegionRewards.remove(i).getBRBonus();
				BVictoryPoints bKingReward = getBoardKingReward();
				return new BVictoryPoints(bRegionReward.getAmount() + bKingReward.getAmount());
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
	 * 
	 * @return the greatest BoardKingReward left in its list; zero
	 *         BVictoryPoints if the list is empty
	 * @see BoardRewardsManager
	 */
	private BVictoryPoints getBoardKingReward() {
		if (this.bKingRewards.isEmpty())
			return new BVictoryPoints(0);
		else
			return this.bKingRewards.remove(0);
	}
}