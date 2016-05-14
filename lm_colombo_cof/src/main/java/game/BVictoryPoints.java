package game;

public class BVictoryPoints extends Bonus {

	private static final int VALUE = 10;
	
	public BVictoryPoints(int amount) {
		super(amount);
	}

	@Override
	public Bonus deepCopy() {
		return new BVictoryPoints(this.getAmount());
	}

	@Override
	public int getValue() {
		return VALUE;
	}
	
}
