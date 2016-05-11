package game;

import java.awt.Color;

public class CouncilorPool {
	private final int NUM_CONC_PER_COLOR = 4;
	private CouncilorColorAvailability[] availCounc;

	public CouncilorPool() {
		initializeAvailCounc();
	}

	private void initializeAvailCounc() {
		availCounc = new CouncilorColorAvailability[ColorConstants.NUM_COUNCILOR_COLORS];
		for (CouncilorColorAvailability temp : availCounc) {
			// placeholder color!
			temp = new CouncilorColorAvailability(NUM_CONC_PER_COLOR, Color.BLACK);
		}
	}

	public boolean isAvailable(Color councColor) {
		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(councColor) && temp.getAvailability() > 0) {
				return true;
			}
		}
		return false;
	}

	public Councilor getCouncilor(Color councColor) throws NegativeException{
		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(councColor)){
				temp.decAvailability();
				return new Councilor(councColor);
			}
		}
		return null;
	}
	
	public Councilor slideCouncilor(Councilor oldCouncilor, Color councColor) throws NegativeException,OverMaxValueException {
		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(councColor)) {
				temp.decAvailability();
			}
			if (temp.getColor().equals(oldCouncilor.getColor())) {
				temp.incAvailability();
			}
		}
		return new Councilor(councColor);
	}

}
