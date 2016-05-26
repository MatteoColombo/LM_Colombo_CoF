package game.board;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class ColorConstants {
	public static final int NUM_COUNCILOR_COLORS = 6;

	// is this hard coded?
	public static final Color SAPPHIRE = new Color(50, 160, 160);
	public static final Color BRONZE = new Color(190, 110, 80);
	public static final Color SILVER = new Color(177, 177, 177);
	public static final Color GOLD = new Color(195, 170, 95);
	
	/*
	 * This constructor is empty because we don't need it
	 */
	private ColorConstants() {
	}
	
	public static List<Color> getCardsColors() {
		return Arrays.asList(Color.BLACK, Color.WHITE, Color.ORANGE, Color.CYAN, Color.PINK, Color.BLUE);	
	}
}
