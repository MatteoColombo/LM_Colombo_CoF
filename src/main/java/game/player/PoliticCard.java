package game.player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class PoliticCard {
	private Color color;
	private boolean isMultipleColor;

	public PoliticCard(ArrayList<Color> possibleColors) {
		Random r = new Random();
		int i = r.nextInt(possibleColors.size() + 1);
		if(i== possibleColors.size())
			isMultipleColor=true;
		else{
			isMultipleColor=false;
			color= possibleColors.get(i);
		}
	}


	public boolean isMultipleColor(){
		return isMultipleColor;
	}
	
	public Color getCardColor() {
		return this.color;
	}

}