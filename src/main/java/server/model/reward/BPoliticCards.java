package server.model.reward;

import server.model.player.Player;
import server.model.player.PoliticCard;

/**
 * A Bonus that assigns an amount of PoliticCards to the Player who is awarded
 * with it.
 * 
 * @see Bonus
 * @see Player
 * @see PoliticCard
 */
public class BPoliticCards extends Bonus {

	private static final long serialVersionUID = -1477598084678660218L;
	private static final int VALUE = 30;
	private static final String NAME = "politic";

	/**
	 * Sets the number of {@link PoliticCard PoliticCards} awarded.
	 * 
	 * @param amount
	 *            the PoliticCards that will be awarded
	 * @see BPoliticCards
	 */
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
		for (int i = 0; i < this.getAmount(); i++) {
			p.drawAPoliticCard();
		}
	}

	@Override
	public String getTagName() {
		return NAME;
	}

}
