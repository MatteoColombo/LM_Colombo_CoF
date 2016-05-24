package game.reward;

import game.player.Player;

public class BTakePermissionCard extends Bonus{
	
	// not really useful here, it may be removed in the future
	private static final int VALUE = 200;
	
	public BTakePermissionCard() {
		super(1);
	}

	@Override
	public int getValue() {
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

}
