package game.reward;

import game.player.Player;

public class BVictoryPoints extends Bonus {

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
	public int getValue() {
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
