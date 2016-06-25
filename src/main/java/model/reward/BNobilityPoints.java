package model.reward;

import model.player.Player;

public class BNobilityPoints extends Bonus {

	private static final long serialVersionUID = -3756662449122371548L;
	private static final int VALUE = 30;
	private static final String NAME = "nobility";
	
	public BNobilityPoints(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BNobilityPoints(amount);
	}
	
	@Override
	protected int getValue() {
		return VALUE;
	}

	@Override
	public void assignBonusTo(Player p) {
		p.getNoblePoints().increaseAmount(this.getAmount());
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}
}
