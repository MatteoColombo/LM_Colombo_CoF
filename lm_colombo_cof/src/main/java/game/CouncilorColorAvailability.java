package game;

import java.awt.Color;

public class CouncilorColorAvailability {
	private Color concColor;
	private final int maxAvailability;
	private int availability;

	public CouncilorColorAvailability(int availability, Color concColor) {
		this.availability = availability;
		this.maxAvailability = availability;
		this.concColor = concColor;
	}

	public Color getColor() {
		return concColor;
	}

	public int getAvailability() {
		return availability;
	}

	public void incAvailability() throws OverMaxValueException {
		if ((availability + 1) <= maxAvailability)
			availability++;
		else
			throw new OverMaxValueException();
	}

	public void decAvailability() throws NegativeException{
		if ((availability - 1) >= 0)
			availability++;
		else
			throw new NegativeException();
	}

}
