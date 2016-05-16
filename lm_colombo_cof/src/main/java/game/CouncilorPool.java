package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CouncilorPool {
	private final int CONC_PER_COLOR;
	private final int COUNCIL_SIZE;
	private final int COLORS_NUMBER;
	private CouncilorColorAvailability[] availCounc;
	private List<Color> colors;
	private int generatedCouncils;
	
	public CouncilorPool(int councPerColor, int councilSize,List<Color> colors) {
		this.CONC_PER_COLOR = councPerColor;
		this.COUNCIL_SIZE=councilSize;
		this.colors=colors;
		this.COLORS_NUMBER=colors.size();
		initializeAvailCounc();
		generatedCouncils=0;
	}

	private void initializeAvailCounc() {
		availCounc = new CouncilorColorAvailability[COLORS_NUMBER];
		for(int i=0;i < COLORS_NUMBER;i++){
			availCounc[i]=new CouncilorColorAvailability(CONC_PER_COLOR, colors.get(i));
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

	private Councilor getCouncilor(Color councColor){
		for (CouncilorColorAvailability temp : availCounc) {
			if (temp.getColor().equals(councColor)) {
				try{
					temp.decAvailability();
				}catch(NegativeException ne){
					System.err.println(ne.getMessage());
				}
			}
		}
		return new Councilor(councColor);
	}

	public Councilor slideCouncilor(Councilor oldCouncilor, Color councColor)
			throws NegativeException, OverMaxValueException, CouncilorNotAvailableException {
		if (!isAvailable(councColor))
			throw new CouncilorNotAvailableException();
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
	
	public Council getCouncil() throws CouncilorNotAvailableException{
		if((generatedCouncils*COUNCIL_SIZE)>((COLORS_NUMBER*CONC_PER_COLOR)-COUNCIL_SIZE))
			throw new CouncilorNotAvailableException();
		Random r= new Random();
		ArrayList<Councilor> generatedCouncil= new ArrayList<Councilor>();
		for(int i=0;i<COUNCIL_SIZE;){
			int temp=r.nextInt(COLORS_NUMBER);
			if(isAvailable(colors.get(temp))){
				generatedCouncil.add(getCouncilor(colors.get(temp)));
				i++;
			}
		}
		generatedCouncils++;
		return new Council(generatedCouncil);
	}

}
