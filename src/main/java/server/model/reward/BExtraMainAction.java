package server.model.reward;

import server.model.action.Action;
import server.model.player.Player;

/**
 * A Bonus that assigns an extra MainAction to the Player who is awarded with
 * it.
 * 
 * @see Action
 * @see Bonus
 * @see Player
 */
public class BExtraMainAction extends Bonus {
	private static final long serialVersionUID = 2652339487323746030L;
	private static final int VALUE = 99;
	private static final String NAME = "extra";

	/**
	 * Sets to one the number of {@link Action MainActions} awarded.
	 * 
	 * @see BExtraMainAction
	 */
	public BExtraMainAction() {
		super(1);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BExtraMainAction();
	}

	@Override
	protected int getValue() {
		return VALUE;
	}

	@Override
	public void assignBonusTo(Player p) {
		p.increaseMainAction();
	}

	@Override
	public String getTagName() {
		return NAME;
	}
}
