package model.board.nobility;

import java.util.List;

import model.player.*;
import model.reward.*;

/**
 * A class that represent the NobilityTrack of the Game.
 * <p>
 * Each Player have NoblePoints that represent the steps of the NobilityTrack
 * where they are that can be increased with the right Bonuses, rewarding with
 * special Rewards, if that step is not <code>null</code>, and BVictoryPoints
 * for who is more forward than the others into it at the end of the Game.
 * 
 * @see Bonus
 * @see BVictoryPoints
 * @see NobilityLoader
 * @see NoblePoints
 * @see Player
 * @see Reward
 */
public class NobilityTrack {
	private List<Reward> track;

	/**
	 * Initializes the NobilityTrack with the available {@link Reward Rewards};
	 * there is <code>null</code> if a step don't have an associated Reward.
	 * 
	 * @param track
	 *            the list of available Rewards
	 * @see NobilityTrack
	 */
	public NobilityTrack(List<Reward> track) {
		this.track = track;
	}

	/**
	 * Returns the requested {@link Reward}.
	 * 
	 * @param index
	 *            the position of the requested Reward
	 * @return the Reward at the requested position
	 * @see NobilityTrack
	 */
	public Reward getReward(int index) {
		return track.get(index);
	}

	/**
	 * Returns the size of the NobilityTrack.
	 * 
	 * @return the size of the NobilityTrack
	 * @see NobilityTrack
	 */
	public int getMaxPoint() {
		return track.size() + 1;
	}
}
