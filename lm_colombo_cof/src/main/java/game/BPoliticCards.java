package game;

public class BPoliticCards extends Bonus {


	public BPoliticCards(int amount) {
		super(amount);
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		return true;
	}

	@Override
	public int getValue() {
		return 40;
	}

	@Override
	public Bonus deepCopy() {
		return new BPoliticCards(this.amount);
	}

	@Override
	public boolean mustBeAlone() {
		return false;
	}

}
