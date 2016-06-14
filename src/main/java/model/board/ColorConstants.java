package model.board;

import javafx.scene.paint.Color;
import java.util.Arrays;
import java.util.List;

public class ColorConstants {
	public static final int NUM_COUNCILOR_COLORS = 6;
	
	/*
	 * This constructor is empty because we don't need it
	 */
	private ColorConstants() {
	}
	
	public static List<Color> getCardsColors() {
		return Arrays.asList(Color.BLACK, Color.WHITE, Color.ORANGE, Color.CYAN, Color.PINK, Color.PURPLE);	
	}
}
