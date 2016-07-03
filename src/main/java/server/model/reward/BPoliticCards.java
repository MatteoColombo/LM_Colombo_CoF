package server.model.reward;

import server.model.player.Player;

/**
 * This bonus gives some extra politic cards to who wins it
 */
public class BPoliticCards extends Bonus {

	private static final long serialVersionUID = -1477598084678660218L;
	private static final int VALUE = 30;
	private static final String NAME = "politic";
	
	/**
	 * Sets the number of politic cards awarded
	 * @param amount
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
		for(int i = 0; i < this.getAmount(); i++) {
			p.drawAPoliticCard();
		}
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}
	
}
