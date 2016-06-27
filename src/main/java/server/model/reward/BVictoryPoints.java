package server.model.reward;

import server.model.player.Player;

public class BVictoryPoints extends Bonus {

	private static final long serialVersionUID = 8781793974974121224L;
	private static final int VALUE = 10;
	private static final String NAME = "victory";
	
	public BVictoryPoints(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BVictoryPoints(amount);
	}

	@Override
	protected int getValue() {
		return VALUE;
	}

	@Override
	public void assignBonusTo(Player p) {
		p.getVictoryPoints().increaseAmount(this.getAmount());
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}
}
