package game;

import java.awt.Color;

public class CouncilorColorAvailability {
	private Color concColor;
	private int availability;

	public CouncilorColorAvailability(int availability, Color concColor) {
		this.availability = availability;
		this.concColor = concColor;
	}
	
	public Color getColor(){
		return concColor;
	}
	
	public int getAvailability(){
		return availability;
	}
	public void incAvailability(){
		availability++;
	}
	
	public void decAvailability(){
		availability--;
	}
	
}
