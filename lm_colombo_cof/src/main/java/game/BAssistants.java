package game;

public class BAssistants extends Bonus {


	public BAssistants(int amount) {
		super(amount);
		this.value = 33;
		this.mustBeAlone = false;
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		return true;
	}

	@Override
	public Bonus deepCopy() {
		return new BAssistants(this.amount);
	}

}
