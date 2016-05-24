package game.reward;

import game.player.Player;

public class BRewardFromPermissionCard extends Bonus {
	
	// not really useful here, it may be removed in the future
	private static final int VALUE = 80;

	public BRewardFromPermissionCard() {
		super(1);
	}

	@Override
	public int getValue() {
		return VALUE;
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BRewardFromPermissionCard();
	}

	@Override
	public void assignBonusTo(Player p) {
		// TODO ask the player for a specific card
	}

}
