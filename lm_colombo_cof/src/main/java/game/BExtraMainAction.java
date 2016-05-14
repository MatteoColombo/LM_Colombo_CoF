package game;

public class BExtraMainAction extends Bonus {

	private static final int VALUE = 99;
	
	public BExtraMainAction() {
		super(1);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BExtraMainAction();
	}

	@Override
	public int getValue() {
		return VALUE;
	}
}
