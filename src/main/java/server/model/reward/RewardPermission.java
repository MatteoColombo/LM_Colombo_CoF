package server.model.reward;

import server.model.player.PermissionCard;

/**
 * A contextual Reward randomly generated.
 * <p>
 * This one is designated specifically for a PermissionCard.
 * 
 * @author Gianpaolo
 * @see Bonus
 * @see PermissionCard
 * @see Reward
 */
public class RewardPermission extends Reward {
	private static final long serialVersionUID = -5265241063177120154L;

	/**
	 * Creates a new {@link Reward} for {@link PermissionCard PermissionCards}
	 * 
	 * @see RewardPermission
	 */
	public RewardPermission() {
		super(Bonus.getAllStandardBonus(), 3, 120);
	}
}
