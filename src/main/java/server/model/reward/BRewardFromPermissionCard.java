package server.model.reward;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.board.nobility.NobilityTrack;
import server.model.player.PermissionCard;
import server.model.player.Player;

/**
 * A Bonus that assigns to a Player the Rewards from a PermissionCard he owns,
 * even if already used.
 * <p>
 * This is a Bonus of the NobilityTrack.
 * 
 * @author Matteo Colombo
 * @see Bonus
 * @see NobilityTrack
 * @see PermissionCard
 * @see Player
 * @see Reward
 */
public class BRewardFromPermissionCard extends Bonus {
	private static final Logger log = Logger.getLogger(BRewardFromPermissionCard.class.getName());
	private static final long serialVersionUID = 7090121575021199364L;
	// not really useful here, it may be removed in the future
	private static final int VALUE = 80;
	private static final String NAME = "fromPermit";

	/**
	 * Sets to one the number of {@link PermissionCard PermissionCards} that can
	 * be chosen.
	 * 
	 * @see BRewardFromPermissionCard
	 */
	public BRewardFromPermissionCard() {
		super(1);
	}

	@Override
	protected int getValue() {
		return VALUE;
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BRewardFromPermissionCard();
	}

	@Override
	public void assignBonusTo(Player p) {
		try {
			p.getClient().askSelectRewardOfPermissionCard();
		} catch (IOException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
	}

	@Override
	public String getTagName() {
		return NAME;
	}

}
