package server.model.reward;

import server.model.player.Nobility;
import server.model.player.Player;

/**
 * A Bonus that assigns an amount of Nobility to the Player who is awarded with
 * it.
 * 
 * @see Bonus
 * @see Nobility
 * @see Player
 */
public class BNobility extends Bonus {
	private static final long serialVersionUID = -3756662449122371548L;
	private static final int VALUE = 30;
	private static final String NAME = "nobility";

	/**
	 * Sets the amount of {@link Nobility} awarded.
	 * 
	 * @param amount
	 *            the value of Nobility that will be awarded
	 * @see BNobility
	 */
	public BNobility(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BNobility(amount);
	}

	@Override
	protected int getValue() {
		return VALUE;
	}

	@Override
	public void assignBonusTo(Player p) {
		p.getNobility().increaseAmount(this.getAmount());
	}

	@Override
	public String getTagName() {
		return NAME;
	}
}
