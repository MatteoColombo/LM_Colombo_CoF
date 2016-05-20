package game.board.council;

import java.awt.Color;

public class Councilor {
	private Color councilorColor;

	/**
	 * 
	 * @param councilorColor the Color of the councilor
	 */
	public Councilor(Color councilorColor) {
		this.councilorColor = councilorColor;
	}

	/**
	 * 
	 * @return the Color of the councilor
	 * 
	 */
	public Color getColor() {
		return councilorColor;
	}
}
