package server.model.reward;

import server.model.player.Player;

public class BExtraMainAction extends Bonus {

	private static final long serialVersionUID = 2652339487323746030L;
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
	protected int getValue() {
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
