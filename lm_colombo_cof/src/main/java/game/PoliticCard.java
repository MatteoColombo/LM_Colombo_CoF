package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class PoliticCard {
	private Color color;

	public PoliticCard(ArrayList<Color> pickedColours) { // still pending if Array or List of String
		int i = new Random().nextInt(pickedColours.size());
		this.color = pickedColours.get(i); // if color==null, then it's the
															// multicolor one that it has been picked
	}

	public Color getCardColor() {
		return this.color;
	}

}