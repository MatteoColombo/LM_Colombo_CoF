package game.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CouncilorPool {
	private final int CONCPERCOLOR;
	private final int COUNCILSIZE;
	private final int COLORSNUMBER;
	private CouncilorColorAvailability[] availCounc;
	private List<Color> colors;
	private int generatedCouncils;

	public CouncilorPool(int councPerColor, int councilSize, List<Color> colors) {
		this.CONCPERCOLOR = councPerColor;
		this.COUNCILSIZE = councilSize;
		this.colors = colors;
		this.COLORSNUMBER = colors.size();
		initializeAvailCounc();
		generatedCouncils = 0;
	}

	private void initializeAvailCounc() {
		availCounc = new CouncilorColorAvailability[COLORSNUMBER];
		for (int i = 0; i < COLORSNUMBER; i++) {
			availCounc[i] = new CouncilorColorAvailability(CONCPERCOLOR, colors.get(i));
		}
	}

	public boolean isAvailable(Color councColor) {
		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(councColor) && temp.getAvailability() > 0) {
				return true;
			}
		}
		return false;
	}

	public boolean isFull(Color councColor) {
		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(councColor) && temp.isFull()) {
				return true;
			}
		}
		return false;
	}

	private Councilor getCouncilor(Color councColor) {
		for (CouncilorColorAvailability temp : availCounc)
			if (temp.getColor().equals(councColor))
				temp.decAvailability();
		return new Councilor(councColor);
	}

	public Councilor slideCouncilor(Councilor oldCouncilor, Color councColor)
			throws NegativeException, OverMaxValueException, CouncilorNotAvailableException {
		if (!isAvailable(councColor))
			throw new CouncilorNotAvailableException();
		if (isFull(oldCouncilor.getColor()))
			throw new OverMaxValueException();
		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(oldCouncilor.getColor())) {
				temp.incAvailability();
			}
		}
		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(councColor)) {
				temp.decAvailability();
			}
		}
		return new Councilor(councColor);
	}

	public Council getCouncil() throws CouncilorNotAvailableException {
		if ((generatedCouncils * COUNCILSIZE) > ((COLORSNUMBER * CONCPERCOLOR) - COUNCILSIZE))
			throw new CouncilorNotAvailableException();
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

}
