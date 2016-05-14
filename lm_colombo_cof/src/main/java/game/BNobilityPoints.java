package game;

public class BNobilityPoints extends Bonus {

	private static final int VALUE = 30;

	
	public BNobilityPoints(int amount) {
		super(amount);
	}

	@Override
	public Bonus deepCopy() {
		return new BNobilityPoints(this.getAmount());
	}
	
	@Override
	public int getValue() {
		return VALUE;
	}
	
}
