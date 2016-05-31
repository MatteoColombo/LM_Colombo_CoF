package model.reward;

import model.player.Player;

public class BExtraMainAction extends Bonus {

	private static final int VALUE = 99;
	private static final String NAME = "extra";
	public BExtraMainAction() {
		super(1);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BExtraMainAction();
	}

	@Override
	public int getValue() {
		return VALUE;
	}

	@Override
	public void assignBonusTo(Player p) {
		p.increaseMainAction();
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}
}
