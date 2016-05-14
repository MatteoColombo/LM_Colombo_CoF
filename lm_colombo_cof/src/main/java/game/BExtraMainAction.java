package game;

public class BExtraMainAction extends Bonus {

	private static final int VALUE = 99;
	
	public BExtraMainAction() {
		super(1);
	}

	@Override
	public Bonus deepCopy() {
		return new BExtraMainAction();
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
