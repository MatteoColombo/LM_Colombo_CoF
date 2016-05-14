package game;

public class BPoliticCards extends Bonus {

	private static final int VALUE = 30;

	public BPoliticCards(int amount) {
		super(amount);
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		return true;
	}

	@Override
	public Bonus deepCopy() {
		return new BPoliticCards(this.getAmount());
	}
	
	@Override
	public int getValue() {
		return VALUE;
	}
	
}
