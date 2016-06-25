package model.reward;

import model.player.Player;

public class BPoliticCards extends Bonus {

	private static final long serialVersionUID = -1477598084678660218L;
	private static final int VALUE = 30;
	private static final String NAME = "politic";
	public BPoliticCards(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BPoliticCards(amount);
	}
	
	@Override
	protected int getValue() {
		return VALUE;
	}

	@Override
	public void assignBonusTo(Player p) {
		for(int i = 0; i < this.getAmount(); i++) {
			p.drawAPoliticCard();
		}
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}
	
}
