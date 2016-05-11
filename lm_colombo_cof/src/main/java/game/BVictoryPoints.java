package game;

public class BVictoryPoints extends Bonus {

	public BVictoryPoints(int amount) {
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
