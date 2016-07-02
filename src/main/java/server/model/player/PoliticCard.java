package server.model.player;

import java.awt.Color;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import server.model.configuration.Configuration;
import server.model.market.Soldable;

/**
 * 
 * @author Davide Cavallini
 *
 */
public class PoliticCard implements Serializable, Soldable {

	private static final long serialVersionUID = 3808938190487455501L;
	private Color color;
	private boolean isMultipleColor;

	/**
	 * This is the constructor which generates a copy of another card
	 * 
	 * @param color
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
	 * This is the constructor which generates a new politic card in case of
	 * multiple colored card, the color is set null
	 * 
	 * @param possibleColors
	 *            the list of the possible colors
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
	 * 
	 * @return true if it's multiple color, false otherwise
	 */
	public boolean isMultipleColor() {
		return this.isMultipleColor;
	}

	/**
	 * 
	 * @return the Color of the card, it's null if it's multiplecolored
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

}