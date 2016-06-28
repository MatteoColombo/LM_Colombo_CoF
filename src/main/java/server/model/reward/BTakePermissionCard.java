package server.model.reward;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.gui.control.MainApp;
import server.model.player.Player;

public class BTakePermissionCard extends Bonus{

	private static final Logger log= Logger.getLogger( BTakePermissionCard.class.getName() );

	
	private static final long serialVersionUID = 4995838499600810957L;
	// not really useful here, it may be removed in the future
	private static final int VALUE = 200;
	private static final String NAME = "takePermit";
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
