package game;

public class BExtraMainAction extends Bonus {

	public BExtraMainAction(int amount) {
		super(amount);
		this.value = 99;
		this.mustBeAlone = true;
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		switch(rt) {
		case CITY: return false;
		case PERMISSION: return true;
		case NOBILITY: return true;
		default: return false;
		}
	}

	@Override
	public Bonus deepCopy() {
		return new BExtraMainAction(this.amount);
	}

}
