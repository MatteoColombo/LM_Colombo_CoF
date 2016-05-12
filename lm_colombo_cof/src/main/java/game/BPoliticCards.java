package game;

public class BPoliticCards extends Bonus {


	public BPoliticCards(int amount) {
		super(amount);
		this.value = 40;
		this.mustBeAlone = false;
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		return true;
	}

	@Override
	public Bonus deepCopy() {
		return new BPoliticCards(this.amount);
	}

}
