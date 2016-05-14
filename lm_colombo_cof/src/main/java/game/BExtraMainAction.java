package game;

public class BExtraMainAction extends Bonus {

	private static final int VALUE = 99;

	
	public BExtraMainAction(int amount) {
		super(amount);
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
		return new BExtraMainAction(this.getAmount());
	}

	@Override
	public int getValue() {
		return VALUE;
	}
	
	@Override
	public void increment(int given) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
}
