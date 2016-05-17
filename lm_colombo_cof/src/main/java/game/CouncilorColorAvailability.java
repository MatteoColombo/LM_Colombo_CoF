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
	
	public boolean isFull(){
		if(availability==maxAvailability)
			return true;
		return false;
	}

	public void incAvailability(){
			availability++;
	}

	public void decAvailability(){
			availability++;
	}

}
