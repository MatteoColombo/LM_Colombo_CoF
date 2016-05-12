package game;

public class BCoins extends Bonus {


	public BCoins(int amount) {
		super(amount);
		this.mustBeAlone = false;
		this.value = 11;
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		return true;
	}

	@Override
	public Bonus deepCopy() {
		return new BCoins(this.amount);
	}

}
