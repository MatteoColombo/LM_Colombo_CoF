package server.model.player;

import java.awt.Color;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import server.model.configuration.Configuration;
import server.model.market.Soldable;

/**
 * A class that represents every PoliticCard in the Game.
 * <p>
 * Each of them has a {@link #getCardColor() Color} that is picked randomly from
 * a list of available ones, included a {@link #isMultipleColor() "multicolor"}
 * one that works as a jolly; they are used to satisfy the Council at which a
 * Player is interested.
 * 
 * @author Davide Cavallini
 * @see Color
 * @see Council
 * @see Player
 */
public class PoliticCard implements Serializable, Soldable {

	private static final long serialVersionUID = 3808938190487455501L;
	private Color color;
	private boolean isMultipleColor;

	/**
	 * Generates a copy of another PoliticCard using his {@link Color}.
	 * 
	 * @param color
	 *            the Color used to create a new PoliticCard
	 * @see PoliticCard
	 */
	public PoliticCard(Color color) {
		if (color != null) {
			this.color = color;
			isMultipleColor = false;
		} else {
			isMultipleColor = true;
		}
	}

	/**
	 * Generates a new PoliticCard using one of the available {@link Color
	 * Colors} chosen randomly; it's set <code>null</code> if it will be the
	 * multicolor one.
	 * 
	 * @param possibleColors
	 *            the list of the possible Colors
	 * @see PoliticCard
	 */
	public PoliticCard(List<Color> possibleColors) {
		Random r = new Random();
		int i = r.nextInt(possibleColors.size() + 1);
		if (i == possibleColors.size())
			isMultipleColor = true;
		else {
			isMultipleColor = false;
			color = possibleColors.get(i);
		}
	}

	/**
	 * Returns if this PoliticCard is a multicolor one or not.
	 * 
	 * @return <code>true</code> if it's a multicolor one; <code>false</code>
	 *         otherwise
	 * @see PoliticCard
	 */
	public boolean isMultipleColor() {
		return this.isMultipleColor;
	}

	/**
	 * Returns the PoliticCard {@link Color}.
	 * 
	 * @return the Color of this PoliticCard; <code>null</code> if it's a
	 *         multicolor one
	 * @see PoliticCard
	 */
	public Color getCardColor() {
		return this.color;
	}

	@Override
	public boolean equals(Object item) {
		if (!(item instanceof PoliticCard))
			return false;
		if (this.isMultipleColor) {
			if (((PoliticCard) item).isMultipleColor)
				return true;
			return false;
		}
		if (this.color.equals(((PoliticCard) item).getCardColor()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		if (isMultipleColor)
			return -1;
		return color.getRGB();
	}

	@Override
	public String getMarketMessage(Configuration config) {
		return "politic " + (this.isMultipleColor ? "multi " : config.getColorsTranslationReverse().get(color) + " ");
	}
	
	@Override
	public Soldable clone(){
		return new PoliticCard(this.color);
	}

}