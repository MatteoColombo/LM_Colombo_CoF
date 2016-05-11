package game;

public class BVictoryPoints extends Bonus {

	public BVictoryPoints(int amount) {
		super(amount);
		this.value = 10;
		this.mustBeAlone = false;
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		return true;
	}

	@Override
	public Bonus deepCopy() {
		return new BVictoryPoints(this.amount);
	}
	
}
