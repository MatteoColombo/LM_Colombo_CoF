package game.player;

import java.awt.Color;
import java.util.List;
import java.util.Random;
/**
 * 
 * @author Davide_Cavallini
 *
 */
public class PoliticCard {
	private Color color;
	private boolean isMultipleColor;
/**
 * 
 * @param possibleColors
 */
	public PoliticCard(List<Color> possibleColors) {
		Random r = new Random();
		int i = r.nextInt(possibleColors.size() + 1);
		if(i== possibleColors.size())
			isMultipleColor=true;
		else{
			isMultipleColor=false;
			color= possibleColors.get(i);
		}
	}

/**
 * 
 * @return
 */
	public boolean isMultipleColor(){
		return isMultipleColor;
	}
/**	
 * 
 * @return
 */
	public Color getCardColor() {
		return this.color;
	}

}