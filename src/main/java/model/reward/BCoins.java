package model.reward;

import model.player.Player;

public class BCoins extends Bonus {

	private static final long serialVersionUID = -2282867100826103325L;
	private static final int VALUE = 11;
	private static final String NAME = "coins";	
	public BCoins(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BCoins(amount);
	}

	@Override
	protected int getValue() {
		return VALUE;
	}

	@Override
	public void assignBonusTo(Player p) {
		p.getCoins().increaseAmount(this.getAmount());
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}
}