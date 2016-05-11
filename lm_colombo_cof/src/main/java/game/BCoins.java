package game;

public class BCoins extends Bonus {


	public BCoins(int amount) {
		super(amount);
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		return true;
	}

	@Override
	public int getValue() {
		return 1;
	}

}
