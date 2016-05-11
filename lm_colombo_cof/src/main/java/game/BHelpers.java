package game;

public class BHelpers extends Bonus {

	public BHelpers(int amount) {
		super(amount);
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		return true;
	}

	@Override
	public int getValue() {
		return 3;
	}

}
