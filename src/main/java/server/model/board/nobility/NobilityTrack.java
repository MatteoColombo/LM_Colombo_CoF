package server.model.board.nobility;

import java.util.List;

import server.model.player.Nobility;
import server.model.player.Player;
import server.model.reward.BVictoryPoints;
import server.model.reward.Bonus;
import server.model.reward.Reward;

/**
 * A class that represent the NobilityTrack of the Game.
 * <p>
 * Each Player have Nobility that represent the steps of the {@link #getTrack()
 * NobilityTrack} where they are that can be increased with the right Bonuses,
 * rewarding with {@link #getReward(int) special Rewards} , if that step is not
 * <code>null</code>, and BVictoryPoints for who is more forward than the others
 * into it at the end of the Game; the NobilityTrack has a {@link #getMaxPoint()
 * max size}.
 * 
 * @see Bonus
 * @see BVictoryPoints
 * @see NobilityLoader
 * @see Nobility
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
	 * Returns the NobilityTrack of the Game.
	 * 
	 * @return the list that represents the NobilityTrack
	 * @see NobilityTrack
	 */
	public List<Reward> getTrack() {
		return this.track;
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
		return track.size()-1;
	}
}
