package server.model.reward;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.model.player.Player;

/**
 * This is a nobility bonus which gives to who wins it a free reward from a permission card
 * @author Matteo Colombo
 *
 */
public class BRewardFromPermissionCard extends Bonus {
	
	private static final Logger log= Logger.getLogger( BRewardFromPermissionCard.class.getName() );
	
	private static final long serialVersionUID = 7090121575021199364L;
	// not really useful here, it may be removed in the future
	private static final int VALUE = 80;
	private static final String NAME = "fromPermit";
	/**
	 * This sets the number of free rewards from permission cards
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
			log.log( Level.SEVERE, e.toString(), e );
		}
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}

}
