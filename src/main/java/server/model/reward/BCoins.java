package server.model.reward;

import server.model.player.Coins;
import server.model.player.Player;

/**
 * A Bonus that assigns an amount of Coins to the Player who is awarded with it.
 * 
 * @see Coins
 * @see Bonus
 * @see Player
 */
public class BCoins extends Bonus {

	private static final long serialVersionUID = -2282867100826103325L;
	private static final int VALUE = 11;
	private static final String NAME = "coins";

	/**
	 * Sets the number of {@link Coins} awarded.
	 * 
	 * @param amount
	 *            the Coins that will be awarded
	 * @see BCoins
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