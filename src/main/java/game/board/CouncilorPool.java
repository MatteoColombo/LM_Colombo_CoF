package game.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.board.council.Council;
import game.board.council.Councilor;
import game.board.council.CouncilorColorAvailability;

public class CouncilorPool {
	private final int CONCPERCOLOR;
	private final int COUNCILSIZE;
	private final int COLORSNUMBER;
	private CouncilorColorAvailability[] availCounc;
	private List<Color> colors;
	private int generatedCouncils;

	/**
	 * It 
	 * 
	 * @param councPerColor number of councilors per color
	 * @param councilSize number of councilors per council
	 * @param colors the list of the colors of the councilors
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
	 *  Sets to max the number of available councilor for each color
	 */
	private void initializeAvailCounc() {
		availCounc = new CouncilorColorAvailability[COLORSNUMBER];
		for (int i = 0; i < COLORSNUMBER; i++) {
			availCounc[i] = new CouncilorColorAvailability(CONCPERCOLOR, colors.get(i));
		}
	}

	/**
	 * Checks if there is at least one councilor for the specified color
	 * @param councColor the color of the councilors
	 * @return true if there is at least 1 councilor, false if there is 0 or less
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
	 * Checks if the stack of councilors for each color has the maximum size
	 * @param councColor the color of the councilors
	 * @return true if the stack is full, false otherwise
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
	 * Decreases the number of the available councilors for the specified color and return an equivalent Councilor
	 * @param councColor the color of the desidered councilor
	 * @return the councilor of the specified color
	 */
	private Councilor getCouncilor(Color councColor) {
		for (CouncilorColorAvailability temp : availCounc)
			if (temp.getColor().equals(councColor))
				temp.decAvailability();
		return new Councilor(councColor);
	}

	/**
	 * Receives the council in which you want to insert a councilor and the color of the councilor that you want to insert
	 * @param council the council in which you will insert a councilor
	 * @param councColor the color of the councilor that you wnat to insert
	 * @return
	 */
	public void slideCouncilor(Council council, Color councColor){
		
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
	 * Generate a random council ad returns it; it is used when a region or the king is initialized
	 * 
	 * @return the generated council
	 */
	public Council getCouncil(){
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
	 * Checks if it is possible to generate another council
	 * @return returns true if it possible to generate another council, false otherwise
	 */
	public boolean canGenerateCouncil(){
		if((generatedCouncils*COUNCILSIZE)> (COLORSNUMBER*CONCPERCOLOR-COUNCILSIZE))
			return false;
		return true;
	}

}
