package game.reward;

import game.player.Player;

public class BPoliticCards extends Bonus {

	private static final int VALUE = 30;

	public BPoliticCards(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BPoliticCards(amount);
	}
	
	@Override
	public int getValue() {
		return VALUE;
	}

	@Override
	public void assignBonusTo(Player p) {
		for(int i = 0; i < this.getAmount(); i++) {
			p.drawAPoliticCard();
		}
	}
	
}
