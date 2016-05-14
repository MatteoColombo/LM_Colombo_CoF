package game;

public class BNobilityPoints extends Bonus {

	private static final int VALUE = 30;

	
	public BNobilityPoints(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BNobilityPoints(amount);
	}
	
	@Override
	public int getValue() {
		return VALUE;
	}
	
}
