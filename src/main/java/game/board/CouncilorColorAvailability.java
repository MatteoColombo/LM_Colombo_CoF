package game.board;

import java.awt.Color;

public class CouncilorColorAvailability {
	private Color concColor;
	private final int maxAvailability;
	private int availability;

	/**
	 * Receives the max number of councilors for this color and theirs color
	 * The availability is also initializaed equals to the max availability
	 * 
	 * @param availability an integer which is the max number of councilors for the specified color
	 * @param concColor the Color of these Councilors
	 */
	public CouncilorColorAvailability(int maxAvailability, Color concColor) {
		this.availability = maxAvailability;
		this.maxAvailability = maxAvailability;
		this.concColor = concColor;
	}

	/**
	 * Return the color of these councilors
	 * @return the Color of the councilors
	 */
	public Color getColor() {
		return concColor;
	}

	/**
	 * Returns the number of available councilors of a color
	 * @return an integer which is the number of available councilors
	 */
	public int getAvailability() {
		return availability;
	}
	
	/**
	 * Checks if the number of the available councilor off map of the color is equal to the max number of available councilors
	 * In fact checks if someone is abusing of the councilor pool
	 * @return a boolean, false if a councilor can still be removed from the map
	 */
	public boolean isFull() {
		if (availability == maxAvailability)
			return true;
		return false;
	}

	/**
	 * Increases the number of councilors of a color out of the map
	 * This mean that one has been removed from a council
	 */
	public void incAvailability() {
		availability++;
	}

	/**
	 * Decreases the number of councilors of a color out of the map.
	 * This means that one has been added to a council
	 */
	public void decAvailability() {
		availability++;
	}

}
