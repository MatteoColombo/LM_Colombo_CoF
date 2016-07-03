package server.model.reward;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.model.player.Player;

/**
 * This is a nobility bonus which gives to who wins it a free permission cards
 * @author Matteo Colombo
 *
 */
public class BTakePermissionCard extends Bonus{

	private static final Logger log= Logger.getLogger( BTakePermissionCard.class.getName() );

	
	private static final long serialVersionUID = 4995838499600810957L;
	// not really useful here, it may be removed in the future
	private static final int VALUE = 200;
	private static final String NAME = "takePermit";
	
	/**
	 * This sets the number of free permission cards
	 */
	public BTakePermissionCard() {
		super(1);
	}

	@Override
	protected int getValue() {
		return VALUE;
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BTakePermissionCard();

	}

	@Override
	public void assignBonusTo(Player p) {
		try {
			p.getClient().askSelectFreePermissionCard();
		} catch (IOException e) {
			log.log( Level.SEVERE, e.toString(), e );
		}
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}

}
