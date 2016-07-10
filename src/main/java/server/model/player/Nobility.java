package server.model.player;

import java.io.Serializable;

import server.model.board.nobility.NobilityTrack;
import server.model.reward.Reward;

/**
 * A class that represents the Nobility owned by a Player.
 * <p>
 * Each Player can have a different {@link #getAmount() value} of it that can be
 * {@link #increaseAmount(int) increased}; so the Player position increases also
 * in the NobilityTrack, with the possibility of receiving a Reward if it's
 * present.
 * 
 * @author Davide Cavallini
 * @see NobilityTrack
 * @see Player
 * @see Reward
 */
public class Nobility implements Serializable {
	private static final long serialVersionUID = 1L;
	private int amount;
	private transient Player owner;
	private transient NobilityTrack track;

	/**
	 * Initializes the initial value of Nobility owned by this {@link Player}.
	 * 
	 * @param initialValue
	 *            the initial value of Nobility to be set
	 * @see Nobility
	 */
	public Nobility(int initialValue, Player owner, NobilityTrack track) {
		this.amount = initialValue;
		this.owner = owner;
		this.track = track;
	}

	/**
	 * Returns the value of Nobility owned in this moment by this {@link Player}
	 * .
	 * 
	 * @return the actual value of Nobility
	 * @see Nobility
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Increases the value of Nobility owned by this {@link Player}; then it
	 * checks if there is a {@link Reward} associated with that position on the
	 * {@link NobilityTrack}, assigning it to this Player if it's present.
	 * 
	 * @param value
	 *            the amount used to increment the actual Nobility
	 * @see Nobility
	 */
	public void increaseAmount(int value) {
		int initial = this.getAmount();
		if ((amount + value) > track.getMaxPoint() && amount < track.getMaxPoint()) {
			amount = track.getMaxPoint();
		} else if ((amount + value) <= track.getMaxPoint()) {
			amount += value;
		}
		for (int i = initial + 1; i <= this.getAmount(); i++) {
			if (track.getReward(i) != null)
				track.getReward(i).assignBonusTo(owner);
		}
	}

}