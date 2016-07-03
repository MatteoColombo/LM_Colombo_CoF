package server.model.reward;

import server.model.player.Player;

/**
 * This bonus gives some coins to the player who receives it
 */
public class BCoins extends Bonus {

	private static final long serialVersionUID = -2282867100826103325L;
	private static final int VALUE = 11;
	private static final String NAME = "coins";	
	/**
	 * Sets the number of coins awarded
	 * @param amount
	 */
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