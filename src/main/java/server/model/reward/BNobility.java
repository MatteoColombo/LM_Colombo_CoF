package server.model.reward;

import server.model.player.Player;

/**
 * This gives to the player who wins it some nobility points
 */
public class BNobility extends Bonus {

	private static final long serialVersionUID = -3756662449122371548L;
	private static final int VALUE = 30;
	private static final String NAME = "nobility";
	
	/**
	 * Sets the amount of nobility points awarded
	 * @param amount
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
