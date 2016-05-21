package game.board.council;

import java.awt.Color;

/**
 * A councilor is characterized by a color and is used in councils
 * @author Matteo Colombo
 *
 */
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
