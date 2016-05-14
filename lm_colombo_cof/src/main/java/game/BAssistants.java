package game;

public class BAssistants extends Bonus {

	private static final int VALUE = 33;
	
	public BAssistants(int amount) {
		super(amount);
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BAssistants(amount);
	}

	@Override
	public int getValue() {
		return VALUE;
	}


}
