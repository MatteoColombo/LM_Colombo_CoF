package server.model.board.council;

import java.awt.Color;

/**
 * A class that represent all the informations about each of the available
 * Colors of a Councilor.
 * <p>
 * In the specific, the CouncilorColorAvailability contains which is
 * {@link #getColor() one of the Colors} and the {@link #getAvailability()
 * number of the available Councilors} of that Color, if its {@link #isFull()
 * availability is already totally used} or if it can be
 * {@link #incAvailability() increased} or {@link #decAvailability() decreased}
 * after one of them has been used or removed from a Council.
 * 
 * @see Color
 * @see Council
 * @see Councilor
 * @see CouncilorPool
 */
public class CouncilorColorAvailability {
	private Color concColor;
	private final int maxAvailability;
	private int availability;

	/**
	 * Initializes the max number of {@link Councilor Councilors} for this
	 * {@link Color} and theirs Color; also the availability is initialized
	 * equals to the max availability.
	 * 
	 * @param availability
	 *            an integer which is the max number of Councilors for the
	 *            specified color
	 * @param concColor
	 *            the Color of these Councilors
	 * @see CouncilorColorAvailability
	 */
	public CouncilorColorAvailability(int maxAvailability, Color concColor) {
		this.availability = maxAvailability;
		this.maxAvailability = maxAvailability;
		this.concColor = concColor;
	}

	/**
	 * Returns the {@link Color} of these {@link Councilor Councilors}.
	 * 
	 * @return the Color of the Councilors
	 * @see CouncilorColorAvailability
	 */
	public Color getColor() {
		return concColor;
	}

	/**
	 * Returns the number of available {@link Councilor Councilors} of a
	 * {@link Color}.
	 * 
	 * @return an integer which is the number of available Councilors
	 * @see CouncilorColorAvailability
	 */
	public int getAvailability() {
		return availability;
	}

	/**
	 * Checks if the number of the available {@link Councilor Councilors} out of
	 * the Map of the {@link Color} is equal to the max number of available
	 * Councilors, checking if someone is abusing of the {@link CouncilorPool}.
	 * 
	 * @return <code>false</code> if a Councilor can still be removed from the
	 *         map; <code>true</code> otherwise
	 * @see CouncilorColorAvailability
	 */
	public boolean isFull() {
		if (availability == maxAvailability)
			return true;
		return false;
	}

	/**
	 * Increases the number of {@link Councilor Councilors} of a {@link Color}
	 * out of the map; this mean that one of them has been removed from a
	 * {@link Council}.
	 * 
	 * @see CouncilorColorAvailability
	 */
	public void incAvailability() {
		availability++;
	}

	/**
	 * Decreases the number of {@link Councilor Councilors} of a {@link Color}
	 * out of the map; this mean that one of them has been added to a
	 * {@link Council}.
	 * 
	 * @see CouncilorColorAvailability
	 */
	public void decAvailability() {
		availability--;
	}

}
