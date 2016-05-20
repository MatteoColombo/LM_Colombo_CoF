package game.board.council;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Council {
	private List<Councilor> councMembers;

	/**
	 * Initializes the council with a list of the inital councilors
	 * 
	 * @param councMember a list of Councilors
	 * 
	 */
	public Council(List<Councilor> councMembers) {
		this.councMembers = councMembers;
	}

	/**
	 * 
	 * Removes the first councilor in the council and adds the received one in tail
	 * @param councilor the one Councilor which has to be added in tail
	 * 
	 */
	public void insertCouncilor(Councilor councilor) {
		councMembers.remove(0);
		councMembers.add(councilor);
	}
	
	/**
	 * Returns the list of the colors of the councilors in the council
	 * @return a list of colors
	 */
	public List<Color> getCouncilorsColor(){
		List<Color> colorList= new ArrayList<>();
		for(Councilor temp: councMembers)
			colorList.add(temp.getColor());
		return colorList;
	}
	
	/**
	 * Returns the color of the councilor in the head of the queue
	 * @return color
	 */
	public Color getHeadColor(){
		return councMembers.get(0).getColor();
	}
	
	
}
