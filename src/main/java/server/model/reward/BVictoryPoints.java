package server.model.reward;

import server.model.player.Player;

/**
 * A Bonus that assigns an amount of VictoryPoints to the Player who is awarded
 * with it.
 * 
 * @see Bonus
 * @see Player
 * @see VictoryPoints
 */
public class BVictoryPoints extends Bonus {
	private static final long serialVersionUID = 8781793974974121224L;
	private static final int VALUE = 10;
	private static final String NAME = "victory";
	
	/**
	 * Sets the number of {@link VictoryPoints} awarded.
	 * 
	 * @param amount
	 *            the VictoryPoints that will be awarded
	 * @see BVictoryPoints
	 */
	public BVictoryPoints(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BVictoryPoints(amount);
	}

	@Override
	protected int getValue() {
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
