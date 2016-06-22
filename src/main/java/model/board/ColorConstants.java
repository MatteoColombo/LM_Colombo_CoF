package model.board;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import model.board.council.Council;
import model.player.PoliticCard;

/**
 * A class that represent {@link #getCardsColors() all the Colors} used for the
 * PoliticCards and the Councils.
 * 
 * @see Color
 * @see Council
 * @see PoliticCard
 */
public class ColorConstants {
	public static final int NUM_COUNCILOR_COLORS = 6;

	/**
	 * Empty constructor that is not used.
	 * 
	 * @see ColorConstants
	 */
	private ColorConstants() {
	}

	/**
	 * Returns all the {@link Color Colors} used for the {@link PoliticCard
	 * PoliticCards} and the {@link Council Councils}.
	 * 
	 * @return all the Colors for the PoliticCards and the Councils
	 * @see ColorConstants
	 */
	public static List<Color> getCardsColors() {
		return Arrays.asList(Color.BLACK, Color.WHITE, Color.ORANGE, Color.CYAN, Color.PINK, Color.BLUE);
	}
}
