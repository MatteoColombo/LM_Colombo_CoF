package model.board.council;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class that represents all the data about the available Councilors.
 * <p>
 * The CouncilorPool has informations about the possibility of
 * {@link #getCouncil() creating a new Council} if {@link #canGenerateCouncil()
 * one of them is still missing} or simply
 * {@link #slideCouncilor(Council, Color) getting the one} in which add a
 * particular Councilor; it also knows, about {@link #getListColor() all the
 * Colors}, if one of them is {@link #isAvailable(Color) still not used} or if
 * there is no more {@link #isFull(Color) availability for one of them}.
 * 
 * @see Color
 * @see Council
 * @see Councilor
 * @see CouncilorColorAvailability
 */
public class CouncilorPool {
	private final int CONCPERCOLOR;
	private final int COUNCILSIZE;
	private final int COLORSNUMBER;
	private CouncilorColorAvailability[] availCounc;
	private List<Color> colors;
	private int generatedCouncils;

	/**
	 * Initializes the CouncilorPool with the list of all the available
	 * {@link Color Colors}, the amount of {@link Councilor Councilors} for each
	 * Color and the size of the {@link Council Councils}.
	 * 
	 * @param councPerColor
	 *            the number of Councilors per Color
	 * @param councilSize
	 *            the number of Councilors per Council
	 * @param colors
	 *            the list of the Colors of the Councilors
	 * @see CouncilorPool
	 */
	public CouncilorPool(int councPerColor, int councilSize, List<Color> colors) {
		this.CONCPERCOLOR = councPerColor;
		this.COUNCILSIZE = councilSize;
		this.colors = colors;
		this.COLORSNUMBER = colors.size();
		initializeAvailCounc();
		generatedCouncils = 0;
	}

	/**
	 * Sets to max the number of available {@link Councilor Councilors} for each
	 * {@link Color}.
	 * 
	 * @see CouncilorPool
	 */
	private void initializeAvailCounc() {
		availCounc = new CouncilorColorAvailability[COLORSNUMBER];
		for (int i = 0; i < COLORSNUMBER; i++) {
			availCounc[i] = new CouncilorColorAvailability(CONCPERCOLOR, colors.get(i));
		}
	}

	/**
	 * Checks if there is at least one {@link Councilor} for the specified
	 * {@link Color}.
	 * 
	 * @param councColor
	 *            the Color of the Councilors that is checked
	 * @return <code>true</code> if there is at least 1 Councilor of that Color;
	 *         <code>false</code> otherwise
	 * @see CouncilorPool
	 */
	public boolean isAvailable(Color councColor) {
		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(councColor) && temp.getAvailability() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the stack of {@link Councilor Councilors} for this
	 * {@link Color} is already at the max size.
	 * 
	 * @param councColor
	 *            the Color of the Councilors
	 * @return <code>true</code> if the stack of that Color is full;
	 *         <code>false</code> otherwise
	 * @see CouncilorPool
	 */
	public boolean isFull(Color councColor) {
		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(councColor) && temp.isFull()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Decreases the number of the available {@link Councilor Councilors} for
	 * the specified {@link Color} and returns one of them.
	 * 
	 * @param councColor
	 *            the Color of the desired Councilor
	 * @return the Councilor of the specified Color
	 * @see CouncilorPool
	 */
	private Councilor getCouncilor(Color councColor) {
		for (CouncilorColorAvailability temp : availCounc)
			if (temp.getColor().equals(councColor))
				temp.decAvailability();
		return new Councilor(councColor);
	}

	/**
	 * Receives the {@link Council} in which insert a {@link Councilor} and the
	 * {@link Color} of the Councilor that is going to be added to it.
	 * 
	 * @param council
	 *            the Council in which will be insert a Councilor
	 * @param councColor
	 *            the Color of the Councilor that is going to be added to it
	 * @see CouncilorPool
	 */
	public void slideCouncilor(Council council, Color councColor) {

		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(council.getHeadColor())) {
				temp.incAvailability();
			}
		}
		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(councColor)) {
				temp.decAvailability();
			}
		}
		council.insertCouncilor(new Councilor(councColor));

	}

	/**
	 * Generate a random {@link Council} and returns it.
	 * 
	 * @return the generated Council
	 * @see CouncilorPool
	 * @see Random
	 */
	public Council getCouncil() {
		Random r = new Random();
		ArrayList<Councilor> generatedCouncil = new ArrayList<>();
		int i = 0;
		while (i < COUNCILSIZE) {
			int temp = r.nextInt(COLORSNUMBER);
			if (isAvailable(colors.get(temp))) {
				generatedCouncil.add(getCouncilor(colors.get(temp)));
				i++;
			}
		}
		generatedCouncils++;
		return new Council(generatedCouncil);
	}

	/**
	 * Checks if it is possible to generate another {@link Council}.
	 * 
	 * @return <code>true</code> if it's possible to generate another Council;
	 *         <code>false</code> otherwise
	 * @see CouncilorPool
	 */
	public boolean canGenerateCouncil() {
		if ((generatedCouncils * COUNCILSIZE) > (COLORSNUMBER * CONCPERCOLOR - COUNCILSIZE))
			return false;
		return true;
	}

	/**
	 * Returns the list of all the {@link Color Colors} of the {@link Councilor
	 * Councilors}.
	 * 
	 * @return the list of all the available Colors
	 * @see CouncilorPool
	 */
	public List<Color> getListColor() {
		return this.colors;
	}

}
