package model.board.council;

import java.awt.Color;
import java.io.Serializable;

/**
 * A councilor is characterized by a color and is used in councils
 * @author Matteo Colombo
 *
 */
public class Councilor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1942234023668222639L;
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
