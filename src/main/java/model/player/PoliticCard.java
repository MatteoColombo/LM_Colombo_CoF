package model.player;

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * TODO JavaDoc
 * 
 * @author Davide Cavallini
 *
 */
public class PoliticCard {
	private Color color;
	private boolean isMultipleColor;

	// this is just for deterministic tests
	public PoliticCard(Color color) {
		this.color = color;
		isMultipleColor = false;
	}
	
	/**
	 * 
	 * @param possibleColors
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
	 * @return
	 */
	public boolean isMultipleColor() {
		return this.isMultipleColor;
	}

	/**
	 * 
	 * @return
	 */
	public Color getCardColor() {
		return this.color;
	}

}