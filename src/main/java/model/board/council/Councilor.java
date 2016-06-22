package model.board.council;

import java.awt.Color;
import java.io.Serializable;

/**
 * A class that represent all the single Councilors that are used in the
 * Councils.
 * <p>
 * Each of them is characterized by a {@link #getColor() Color}.
 * 
 * @author Matteo Colombo
 * @see Color
 * @see Council
 * @see CouncilorColorAvailability
 * @see CouncilorPool
 */
public class Councilor implements Serializable {
	private static final long serialVersionUID = -1942234023668222639L;
	private Color councilorColor;

	/**
	 * Initializes a Councilor with its own {@link Color}.
	 * 
	 * @param councilorColor
	 *            the Color of the Councilor
	 * @see Councilor
	 */
	public Councilor(Color councilorColor) {
		this.councilorColor = councilorColor;
	}

	/**
	 * Returns the {@link Color} of the Councilor.
	 * 
	 * @return the Color of the Councilor
	 * @see Councilor
	 */
	public Color getColor() {
		return councilorColor;
	}
}
