package server.model.reward;

import server.model.player.Player;

public class BTakePermissionCard extends Bonus{

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
		// TODO ask the player for a permission card
		
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}

}
