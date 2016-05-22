package game.reward;

import game.player.Player;

public class BCoins extends Bonus {

	private static final int VALUE = 11;

	public BCoins(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BCoins(amount);
	}

	@Override
	public int getValue() {
		return VALUE;
	}

	@Override
	public void assignBonusTo(Player p) {
		p.getCoins().increaseAmount(this.getAmount());
	}
}