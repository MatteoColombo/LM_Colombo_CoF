package server.model.reward;

import server.model.player.Player;

public class BRewardFromPermissionCard extends Bonus {
	
	private static final long serialVersionUID = 7090121575021199364L;
	// not really useful here, it may be removed in the future
	private static final int VALUE = 80;
	private static final String NAME = "fromPermit";
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
		// TODO ask the player for a specific card
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}

}
