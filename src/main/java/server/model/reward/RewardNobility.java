package server.model.reward;

import server.model.board.nobility.NobilityTrack;

/**
 * A contextual Reward randomly generated.
 * <p>
 * This one is designated specifically for the NobilityTrack.
 * 
 * @author Gianpaolo
 * @see Bonus
 * @see NobilityTrack
 * @see Reward
 */
public class RewardNobility extends Reward {

	private static final long serialVersionUID = -1108948848681251626L;

	/**
	 * Creates a new {@link Reward} for the {@link NobilityTrack}
	 * 
	 * @see RewardNobility
	 */
	public RewardNobility() {
		super(Bonus.getNobilityBonus(), 2, 80);
	}
}
