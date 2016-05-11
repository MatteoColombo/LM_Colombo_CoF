package game;

public class BExtraRewardFromCity extends Bonus {

	public BExtraRewardFromCity(int amount) {
		super(amount);
		this.value = 98;
		this.mustBeAlone = true;
	}

	@Override
	public boolean isInstantiableFor(RewardType rt) {
		switch(rt) {
		case CITY: return false;
		case PERMISSION: return false;
		case NOBILITY: return true;
		default: return false;
		}
	}

	@Override
	public int getValue() {
		return this.value;
	}

	@Override
	public boolean mustBeAlone() {
		return this.mustBeAlone;
	}

	@Override
	public Bonus deepCopy() {
		return new BExtraRewardFromCity(this.amount);
	}

}
