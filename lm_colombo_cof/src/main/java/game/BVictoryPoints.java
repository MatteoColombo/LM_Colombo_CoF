package game;

public class BVictoryPoints extends Bonus {

	private static final int VALUE = 10;
	
	public BVictoryPoints(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BVictoryPoints(amount);
	}

	@Override
	public int getValue() {
		return VALUE;
	}
	
}
