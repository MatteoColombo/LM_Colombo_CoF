package server.model.reward;

import server.model.player.Player;

public class BAssistants extends Bonus {

	private static final long serialVersionUID = 7786633864808076814L;
	private static final int VALUE = 33;
	private static final String NAME = "assistants";
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
