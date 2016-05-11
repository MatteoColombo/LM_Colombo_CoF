package game;

public class BNobilityPoints extends Bonus {

	public BNobilityPoints(int amount) {
		super(amount);
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		switch(rt) {
		case CITY: return true;
		case PERMISSION: return true;
		case NOBILITY: return false;
		default: return false;
		}
	}

	@Override
	public int getValue() {
		return 2;
	}

}
