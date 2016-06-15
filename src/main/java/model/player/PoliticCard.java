package model.player;

import java.awt.Color;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * TODO JavaDoc
 * 
 * @author Davide Cavallini
 *
 */
public class PoliticCard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3808938190487455501L;
	private transient Color color;
	private boolean isMultipleColor;
	
	public PoliticCard(Color color) {
		if(color != null) {
			this.color = color;
			isMultipleColor = false;
		} else {
			isMultipleColor = true;
		}
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