package game.board;

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
	public void insertCounciler(Councilor councilor) {
		councMembers.remove(0);
		councMembers.add(councilor);
	}
	
	/**
	 * Returns a Councilor in the specified position in the array list
	 * @param pos an integer which indiacates the position
	 * @return the councilor in the specified position
	 */

	public Councilor getCouncilor(int pos) {
		return councMembers.get(pos);
	}
}
