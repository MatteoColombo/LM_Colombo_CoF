package server.model.player;

import java.io.Serializable;

import server.model.board.nobility.NobilityTrack;
import server.model.reward.Reward;

/**
 * A class that represents the NoblePoints owned by a Player.
 * <p>
 * Each Player can have a different {@link #getAmount() amount} of them that can
 * be {@link #increaseAmount(int) increased}.
 * 
 * @author Davide Cavallini
 * @see Player
 */
public class NoblePoints implements Serializable{

	private static final long serialVersionUID = 1L;
	private int amount;
	private transient Player owner;
	private transient NobilityTrack track;

	/**
	 * Initializes the initial amount of NoblePoints owned by this
	 * {@link Player}.
	 * 
	 * @param initialValue
	 *            the initial amount of NoblePoints to be set
	 * @see NoblePoints
	 */
	public NoblePoints(int initialValue, Player owner, NobilityTrack track) {
		this.amount = initialValue;
		this.owner=owner;
		this.track=track;
	}

	/**
	 * Returns the amount of NoblePoints owned in this moment by this
	 * {@link Player}.
	 * 
	 * @return the actual amount of NoblePoints
	 * @see NoblePoints
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Increases the amount of NoblePoints owned by this {@link Player}.
	 * 
	 * @param value
	 *            the amount used to increment the actual NoblePoints
	 * @see NoblePoints
	 */
	public void increaseAmount(int value) {
		if((amount + value)> track.getMaxPoint() && amount < track.getMaxPoint()){
			amount=track.getMaxPoint();
			Reward rew=track.getReward(amount);
			if(rew != null)
				rew.assignBonusTo(owner);
		}else if((amount + value) < track.getMaxPoint()){
			amount+=value;
			Reward rew=track.getReward(amount);
			if(rew != null)
				rew.assignBonusTo(owner);
		}		
	}

}