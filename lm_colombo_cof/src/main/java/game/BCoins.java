package game;

public class BCoins extends Bonus {

	private static final int VALUE = 11;

	public BCoins(int amount) {
		super(amount);
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		return true;
	}

	@Override
	public Bonus deepCopy() {
		return new BCoins(this.getAmount());
	}

	@Override
	public int getValue() {
		return VALUE;
	}

}
