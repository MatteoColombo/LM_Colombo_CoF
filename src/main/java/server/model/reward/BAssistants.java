package server.model.reward;

import server.model.player.Player;
import server.model.player.Assistants;

/**
 * A Bonus that assigns an amount of Assistants to the Player who is awarded
 * with it.
 * 
 * @see Assistants
 * @see Bonus
 * @see Player
 */
public class BAssistants extends Bonus {
	private static final long serialVersionUID = 7786633864808076814L;
	private static final int VALUE = 33;
	private static final String NAME = "assistants";

	/**
	 * Sets the number of {@link Assistants} awarded.
	 * 
	 * @param amount
	 *            the Assistants that will be awarded
	 * @see BAssistants
	 */
	public BAssistants(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BAssistants(amount);
	}

	@Override
	protected int getValue() {
		return VALUE;
	}

	@Override
	public void assignBonusTo(Player p) {
		p.getAssistants().increaseAmount(this.getAmount());
	}

	@Override
	public String getTagName() {
		return NAME;
	}
}
