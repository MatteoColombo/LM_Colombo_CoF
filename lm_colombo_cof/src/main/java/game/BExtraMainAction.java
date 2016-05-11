package game;

public class BExtraMainAction extends Bonus {

	public BExtraMainAction(int amount) {
		super(amount);
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		switch(rt) {
		case CITY: return false;
		case PERMISSION: return false;
		case NOBILITY: return true;
		default: return false;
		}
	}

	@Override
	public int getValue() {
		return 99;
	}

	@Override
	public Bonus deepCopy() {
		return new BExtraMainAction(this.amount);
	}

	@Override
	public boolean mustBeAlone() {
		return true;
	}

}
